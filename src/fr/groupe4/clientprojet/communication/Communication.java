package fr.groupe4.clientprojet.communication;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;

import org.jetbrains.annotations.Async;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.communication.enums.*;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;

/**
 * Communication, effectue les appels API.
 * Les appels sont effectués en instance pour thread la connection et éviter de bloquer le thread courant.
 * <br><br>
 * Cette classe utilise le pattern Builder.
 * <br><br>
 * Exemple d'utilisation : <br><code>
 *      Communication comm = Communication.builder() <br>
 *                                        .connect("username", "password") <br>
 *                                        .build(); <br>
 *      comm.start(); <br>
 *      comm.sleepUntilFinished(); </code><br>
 * <br>
 * Autre exemple : <br><code>
 *      Communication comm = Communication.builder() <br>
 *                                        .startNow() <br>
 *                                        .sleepUntilFinished() <br>
 *                                        .connect("username", "password") <br>
 *                                        .build();</code><br>
 * <br>
 * Résultats de la connexion : <br><code>
 *      comm.getStatus(); // success <br>
 *      comm.getCode(); // SUCCESS_AUTHENTICATED <br>
 *      comm.getMessage(); // Authentication successful and JWT generated. <br>
 *      comm.getHtmlCode(); // 200 <br>
 *      Communication.isConnected(); // true </code><br>
 *
 * @author Romain
 */
@SuppressWarnings("unused")
public final class Communication implements Runnable {
    /**
     * Client HTTP pour les requètes
     */
    @NotNull
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    /**
     * URL de l'API
     */
    @NotNull
    private static final String baseApiUrl = "https://api.ythepaut.com/g4/actions";

    /**
     * Temps avant de timeout
     */
    @NotNull
    private static final Duration TIMEOUT_DELAY = Duration.ofSeconds(30);

    /**
     * Temps avant d'actualiser
     */
    @NotNull
    private static final Duration UPDATE_DELAY = Duration.ofMillis(10);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Token utilisé pour les requêtes, null si non connecté
     */
    @NotNull
    private static volatile AtomicReference<String> requestToken = new AtomicReference<>("");

    /**
     * Token utilisé pour renouveler requestToken
     */
    @NotNull
    private static volatile AtomicReference<String> renewToken = new AtomicReference<>("");

    /**
     * Si les threads ont le droit de communiquer ou non
     */
    private static volatile boolean communicationAllowed = true;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter du token de requête
     *
     * @param editor Qui veut accéder au token ? JsonTreatment ou CommunicationBuilder seulement sont autorisés
     *
     * @return Token
     */
    @NotNull
    static synchronized String getRequestToken(@NotNull Object editor) {
        if (editor instanceof CommunicationBuilder || editor instanceof JsonTreatment) {
            return requestToken.get();
        }
        else {
            Logger.error("Accès au token non autorisé");
            return "";
        }
    }

    /**
     * Setter du token de requête
     *
     * @param editor Qui veut accéder au token ? JsonTreatment seulement est autorisés
     * @param token Token
     */
    @SuppressWarnings("SameParameterValue")
    static synchronized void setRequestToken(@NotNull Object editor, @NotNull String token) {
        if (editor instanceof JsonTreatment) {
            requestToken.set(token);
        }
        else {
            Logger.error("Accès au token non autorisé");
        }
    }

    /**
     * Getter du token de renew
     *
     * @param editor Qui veut accéder au token ? JsonTreatment ou CommunicationBuilder seulement sont autorisés
     *
     * @return Token
     */
    @NotNull
    static synchronized String getRenewToken(@NotNull Object editor) {
        if (editor instanceof CommunicationBuilder || editor instanceof JsonTreatment) {
            return renewToken.get();
        }
        else {
            Logger.error("Accès au token non autorisé");
            return "";
        }
    }

    /**
     * Setter du token de renew
     *
     * @param editor Qui veut accéder au token ? JsonTreatment seulement est autorisés
     * @param token Token
     */
    @SuppressWarnings("SameParameterValue")
    static synchronized void setRenewToken(@NotNull Object editor, @NotNull String token) {
        if (editor instanceof JsonTreatment) {
            if (token.isEmpty()) {
                renewToken.set("");
            }
            else {
                renewToken.set(token);
            }
        }
        else {
            Logger.error("Accès au token non autorisé");
        }
    }

    /**
     * Vérifie l'état de la connexion
     *
     * @return Connecté ou non
     */
    public static boolean isConnected() {
        checkTokenValidity();
        return !requestToken.get().isEmpty();
    }

    /**
     * Vérifie la validité d'un token
     */
    private static synchronized void checkTokenValidity() {
        if (!requestToken.get().isEmpty()) {
            String[] splitString = requestToken.get().split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String tokenBody = new String(decoder.decode(splitString[1]));

            JSONParser parser = new JSONParser();

            try {
                JSONObject parsedResponse = (JSONObject) parser.parse(tokenBody);

                long currentTime = System.currentTimeMillis() / 1000L;

                long expirationTime = (long) parsedResponse.get("exp");

                // long totalTime = expirationTime - (long) parsedResponse.get("iat");

                long remainingTime = expirationTime - currentTime;

                if (remainingTime < TIMEOUT_DELAY.toSeconds()*2) {
                    requestToken.set("");
                }
            }
            catch (ParseException e) {
                Logger.error("Vérification de token invalide");
            }
        }
    }

    /**
     * Quitte tous les threads
     */
    public static void exit() {
        communicationAllowed = false;
    }

    /**
     * Transforme une HashMap en formulaire pour POST <br>
     * Source : <a href="https://mkyong.com/java/how-to-send-http-request-getpost-in-java/">mkyong.com</a>
     *
     * @param data Data en entrée
     *
     * @return Formulaire pour POST
     */
    @NotNull
    private static HttpRequest.BodyPublisher buildFormDataFromMap(@NotNull HashMap<String, Object> data) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }

        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    /**
     * Builder de la communication
     */
    @NotNull
    public static CommunicationBuilder builder() {
        return new CommunicationBuilder();
    }

    // TODO
    public static Communication getInstance(CommunicationKeepAlive type) {
        return singletons.get(type);
    }

    // TODO
    private static HashMap<CommunicationKeepAlive, Communication> singletons;

    // TODO
    static {
        singletons = new HashMap<>();
        // singletons.put(KEEP_ALIVE_LIST_MESSAGE, Communication.builder().getUserMessageList(0).startNow().build());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Utile pour pattern Observer/Observable
     */
    @NotNull
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Data de la requête
     */
    @NotNull
    private final HashMap<String, Object> requestData;

    /**
     * Résultat de la communication
     */
    @Nullable
    Object communicationResult;

    /**
     * Maintient la connexion en vie ou non
     */
    private final boolean keepAlive;

    /**
     * Requête autorisée ou non
     */
    private boolean requestAllowed;

    /**
     * Temps entre deux requêtes
     */
    @NotNull
    private final Duration timeBetweenRequests;

    /**
     * Client ayant finit son chargement ou non
     */
    private boolean loadingFinished;

    /**
     * Thread lancé ou non
     */
    private boolean started;

    /**
     * Type de communication
     */
    @NotNull
    final CommunicationType typeOfCommunication;

    /**
     * Statut de la requête, comme "success" ou "error"
     */
    @NotNull
    CommunicationStatus status;

    /**
     * Code de l'API, par exemple "ERROR_INVALID_USER_CREDENTIALS" ou "SUCCESS_AUTHENTICATED"
     */
    @NotNull
    private APICode code;

    /**
     * Code HTML de la requête, comme un code 200 (OK) ou 404 (Not Found)
     */
    @NotNull
    HTMLCode htmlCode;

    /**
     * Message associé à la requête
     */
    @NotNull
    private String message;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructeur de la Communication
     *
     * @param builder Builder de la communication
     */
    Communication(@NotNull CommunicationBuilder builder) {
        started = false;
        keepAlive = builder.keepAlive;
        requestData = builder.requestData;
        communicationResult = null;
        typeOfCommunication = builder.typeOfCommunication;
        status = CommunicationStatus.STATUS_DEFAULT;
        code = APICode.NOT_FINISHED;
        requestAllowed = true;
        htmlCode = HTMLCode.HTML_CUSTOM_DEFAULT_ERROR;
        message = "";
        timeBetweenRequests = Duration.ofSeconds(10);

        if (builder.startNow) {
            start();
        }

        if (builder.sleepUntilFinished) {
            if (started) {
                sleepUntilFinished();
            }
            else {
                Logger.warning("sleepUntilFinished sur communication non démarrée");
            }
        }
    }

    /**
     * Retourne le statut de la requête, comme "success" ou "error"
     *
     * @return Statut
     */
    @NotNull
    public CommunicationStatus getStatus() {
        return status;
    }

    /**
     * Retourne le code de l'API, par exemple "ERROR_INVALID_USER_CREDENTIALS" ou "SUCCESS_AUTHENTICATED"
     *
     * @return Code API
     */
    @NotNull
    public APICode getCode() {
        return code;
    }

    /**
     * Retourne le message associé à la requête
     *
     * @return Message
     */
    @NotNull
    public String getMessage() {
        return message.isEmpty() ? "Erreur inconnue" : message;
    }

    /**
     * Commencé ou non
     *
     * @return Commencé ?
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Retourne le code HTML de la requête, comme un code 200 (OK) ou 404 (Not Found)
     *
     * @return Code HTML
     */
    @NotNull
    public HTMLCode getHtmlCode() {
        return htmlCode;
    }

    /**
     * Renvoie le résultat de l'action
     *
     * @return Résultat
     */
    @Nullable
    public Object getResult() {
        return communicationResult;
    }

    /**
     * Requête terminée ou non
     *
     * @return Requête terminée ?
     */
    public boolean isFinished() {
        return loadingFinished;
    }

    /**
     * Succès de l'action ou non
     *
     * @return Succès
     */
    public boolean isSuccessful() {
        return htmlCode == HTMLCode.HTML_OK;
    }

    /**
     * Annule la requête
     */
    public void cancelRequest() {
        requestAllowed = false;

        while (!loadingFinished) {
            try {
                Thread.sleep(UPDATE_DELAY.toMillis()/10);
            } catch (InterruptedException ignored) {}
        }
    }

    /**
     * Affiche la communication
     *
     * @return String
     */
    @Override @NotNull
    public String toString() {
        return htmlCode
                + " | " + status
                + " | " + code
                + " | " + typeOfCommunication
                + " | started=" + started
                + " | loadingFinished=" + loadingFinished
                + " | " + message;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Lance le Thread
     */
    public void start() {
        if (!started) {
            started = true;
            Thread t = new Thread(this);
            t.start();
        }
    }

    /**
     * Méthode pour connecter l'instance à son URL
     */
    @Async.Execute
    private void send() {
        // Requête API
        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(requestData))
                .uri(URI.create(baseApiUrl + typeOfCommunication.getUrl()))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(TIMEOUT_DELAY)
                .build();

        // Réponse de l'API en cours
        CompletableFuture<HttpResponse<String>> requestSent = httpClient.sendAsync(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        // Vraie réponse
        HttpResponse<String> response = null;

        try {
            while (!requestSent.isDone()) {
                if (communicationAllowed && requestAllowed) {
                    // Mise en pause du thread jusqu'à ce que la requête soit validée
                    Thread.sleep(UPDATE_DELAY.toMillis());
                }
                else {
                    requestSent.cancel(true);
                }
            }

            response = requestSent.get();
        }
        catch (InterruptedException e) {
            Logger.error("Erreur inconnue :", e, toString());
        }
        catch (ExecutionException e) {
            htmlCode = HTMLCode.HTML_CUSTOM_TIMEOUT;
            Logger.error("Requête time out :", e, toString());
        }
        catch (CancellationException e) {
            htmlCode = HTMLCode.HTML_CUSTOM_CANCEL;
            Logger.error("Requête annulée :", e, toString());
        }

        if (response != null) {
            htmlCode = HTMLCode.fromInt(response.statusCode());

            JSONParser parser = new JSONParser();
            Object parsedResponse = null;

            try {
                parsedResponse = parser.parse(response.body());
            } catch (ParseException e) {
                Logger.error(
                        "Réponse invalide, erreur serveur ? Réponse serveur :",
                        response.body(),
                        toString()
                );
            }

            if (null != parsedResponse) {
                JSONObject jsonMain = (JSONObject) parsedResponse;

                status = CommunicationStatus.fromString((String) jsonMain.get("status"));
                code = APICode.fromString((String) jsonMain.get("code"));
                message = (String) jsonMain.get("message");

                Object jsonObject = jsonMain.get("content");

                if (htmlCode != HTMLCode.HTML_OK) {
                    Logger.debug("Code NOK :", toString(), LoggerOption.LOG_FILE_ONLY);
                }

                JsonTreatment.doSomethingWithData(this, jsonObject);
            }
        }
    }

    /**
     * Met en pause le thread actuel le temps que la requête soit effectuée
     */
    public void sleepUntilFinished() {
        while (started && !loadingFinished && communicationAllowed) {
            try {
                Thread.sleep(UPDATE_DELAY.toMillis());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lance le thread de connexion à l'API
     */
    @Override
    @Async.Execute
    public void run() {
        started = true;

        if (typeOfCommunication.checkConnection()) {
            checkTokenValidity();

            if (requestToken.get().isEmpty()) {
                // Si le token est périmé on le recrée

                new CommunicationBuilder()
                        .startNow()
                        .sleepUntilFinished()
                        .updateConnection()
                        .build();

                if (requestToken.get().isEmpty()) {
                    // S'il n'est pas recréé, euh oups
                    Logger.error("Token invalide une 2nde fois", toString());
                }
                else {
                    // Si jeton recréé, on reprend

                    if (requestData.get("token") != null) {
                        requestData.remove("token");
                        requestData.put("token", requestToken);
                    }
                }
            }
        }

        send();

        loadingFinished = true;
        propertyChangeSupport.firePropertyChange(
                CommunicationPropertyName.COMMUNICATION_LOADING_FINISHED.toString(),
                false,
                true
        );

        if (keepAlive && communicationAllowed) {
            try {
                Thread.sleep(timeBetweenRequests.toMillis());

            }
            catch (InterruptedException e) {
                Logger.error("Erreur pour keepAlive :", toString());
            }
        }
    }

    /**
     * Ajoute un listener pour le pattern observer
     *
     * @param listener Listener
     */
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
        if (started) {
            Logger.warning(
                    "Attention !",
                    "Par sécurité, ne pas ajouter de listener alors que le thread est déjà lancé,",
                    "et donc potentiellement déjà terminé !"
            );
        }

        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Supprime un listener pour le pattern observer
     *
     * @param listener Listener
     */
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}