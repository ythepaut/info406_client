package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.communication.enums.CommunicationStatus;
import fr.groupe4.clientprojet.communication.enums.CommunicationType;
import fr.groupe4.clientprojet.communication.enums.HTMLCode;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import java.util.HashMap;
import java.util.Observable;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static fr.groupe4.clientprojet.communication.enums.CommunicationType.*;
import static fr.groupe4.clientprojet.communication.enums.HTMLCode.*;

/**
 * Communication, effectue les appels API <br>
 * Les appels sont effectués en instance pour thread la connection et éviter de bloquer le thread courant <br>
 * <br>
 * Cette classe utilise le pattern Builder <br>
 * <br>
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
 * Résultats de la connexion : <br>
 *      comm.getStatus(); // success <br>
 *      comm.getCode(); // SUCCESS_AUTHENTICATED <br>
 *      comm.getMessage(); // Authentication successful and JWT generated. <br>
 *      comm.getHtmlCode(); // 200 <br>
 *      Communication.isConnected(); // true <br>
 *
 * @author Romain
 */
public final class Communication extends Observable implements Runnable {
    /**
     * Client HTTP pour les requètes
     */
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    /**
     * URL de l'API
     */
    private static final String baseApiUrl = "https://api.ythepaut.com/g4/actions/";

    /**
     * Temps avant de timeout
     */
    private static final Duration TIMEOUT_DELAY = Duration.ofSeconds(30);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Token utilisé pour les requêtes, null si non connecté
     */
    private static volatile String requestToken = null;

    /**
     * Token utilisé pour renouveler requestToken
     */
    private static volatile String renewToken = null;

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
    protected static synchronized String getRequestToken(Object editor) {
        if (editor instanceof CommunicationBuilder || editor instanceof JsonTreatment) {
            return requestToken;
        }
        else {
            System.err.println("Accès au token non autorisé");
            return "";
        }
    }

    /**
     * Setter du token de requête
     *
     * @param editor Qui veut accéder au token ? JsonTreatment seulement est autorisés
     * @param token Token
     */
    protected static synchronized void setRequestToken(Object editor, String token) {
        if (editor instanceof JsonTreatment) {
            if (token == null) {
                requestToken = null;
            }
            else if (token.isEmpty()) {
                requestToken = null;
            }
            else {
                requestToken = token;
            }
        }
        else {
            System.err.println("Accès au token non autorisé");
        }
    }

    /**
     * Getter du token de renew
     *
     * @param editor Qui veut accéder au token ? JsonTreatment ou CommunicationBuilder seulement sont autorisés
     *
     * @return Token
     */
    protected static synchronized String getRenewToken(Object editor) {
        if (editor instanceof CommunicationBuilder || editor instanceof JsonTreatment) {
            return renewToken;
        }
        else {
            System.err.println("Accès au token non autorisé");
            return "";
        }
    }

    public static void cassetout() {
        requestToken = "lalala";
    }

    /**
     * Setter du token de renew
     *
     * @param editor Qui veut accéder au token ? JsonTreatment seulement est autorisés
     * @param token Token
     */
    protected static synchronized void setRenewToken(Object editor, String token) {
        if (editor instanceof JsonTreatment) {
            if (token == null) {
                renewToken = null;
            }
            else if (token.isEmpty()) {
                renewToken = null;
            }
            else {
                renewToken = token;
            }
        }
        else {
            System.err.println("Accès au token non autorisé");
        }
    }

    /**
     * Vérifie l'état de la connexion
     *
     * @return Connecté ou non
     */
    public static boolean isConnected() {
        return requestToken != null;
    }

    /**
     * Quitte tous les threads
     */
    public static void exit() {
        communicationAllowed = false;
    }

    /**
     * Transforme une HashMap en formulaire pour POST <br>
     * https://mkyong.com/java/how-to-send-http-request-getpost-in-java/
     *
     * @param data Data en entrée
     *
     * @return Formulaire pour POST
     */
    private static HttpRequest.BodyPublisher buildFormDataFromMap(HashMap<String, String> data) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }

        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    /**
     * Builder de la communication
     */
    public static CommunicationBuilder builder() {
        return new CommunicationBuilder();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Data de la requête
     */
    private HashMap<String, String> requestData;

    /**
     * Résultat de la communication
     */
    protected Object communicationResult;

    /**
     * Client ayant finit son chargement ou non
     */
    private boolean loadingFinished;

    /**
     * Type de communication
     */
    protected CommunicationType typeOfCommunication;

    /**
     * URL à envoyer à l'API
     */
    private String url;

    /**
     * Statut de la requête, comme "success" ou "error"
     */
    protected CommunicationStatus status;

    /**
     * Code de l'API, par exemple "ERROR_INVALID_USER_CREDENTIALS" ou "SUCCESS_AUTHENTICATED"
     */
    private String code;

    /**
     * Code HTML de la requête, comme un code 200 (OK) ou 404 (Not Found)
     */
    protected HTMLCode htmlCode;

    /**
     * Message associé à la requête
     */
    private String message;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructeur de la Communication
     *
     * @param builder Builder de la communication
     */
    protected Communication(CommunicationBuilder builder) {
        requestData = builder.requestData;
        communicationResult = null;
        typeOfCommunication = builder.typeOfCommunication;
        url = builder.url;
        status = null;
        code = null;
        htmlCode = HTML_CUSTOM_DEFAULT_ERROR;
        message = null;

        Thread t = new Thread(this);

        if (builder.startNow) {
            t.start();

            if (builder.sleepUntilFinished) {
                sleepUntilFinished();
            }
        }
    }

    /**
     * Retourne le statut de la requête, comme "success" ou "error"
     *
     * @return Statut
     */
    public String getStatus() {
        return status.toString();
    }

    /**
     * Retourne le code de l'API, par exemple "ERROR_INVALID_USER_CREDENTIALS" ou "SUCCESS_AUTHENTICATED"
     *
     * @return Code API
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourne le message associé à la requête
     *
     * @return Message
     */
    public String getMessage() {
        if (message == null) {
            return "Erreur inconnue";
        }
        else {
            return message;
        }
    }

    /**
     * Retourne le code HTML de la requête, comme un code 200 (OK) ou 404 (Not Found)
     *
     * @return Code HTML
     */
    public int getHtmlCode() {
        return htmlCode.getCode();
    }

    /**
     * Renvoie le résultat de l'action
     *
     * @return Résultat
     */
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Méthode pour connecter l'instance à son URL
     */
    private void send() {
        // Requête API
        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(requestData))
                .uri(URI.create(baseApiUrl + url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(TIMEOUT_DELAY)
                .build();

        HttpResponse<String> response = null;
        // Réponse de l'API

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException e) {
            htmlCode = HTML_CUSTOM_TIMEOUT;
            System.err.println("Connection timed out");
        }
        catch (InterruptedException e) {
            htmlCode = HTML_CUSTOM_DEFAULT_ERROR;
            System.err.println("Requête interrompue");
        }

        if (response != null) {
            htmlCode = HTMLCode.fromInt(response.statusCode());

            JSONParser parser = new JSONParser();
            Object parsedResponse = null;

            try {
                parsedResponse = parser.parse(response.body());
            } catch (ParseException e) {
                System.err.println("Réponse invalide");
            }

            if (parsedResponse != null) {
                JSONObject jsonMain = (JSONObject) parsedResponse;

                status = CommunicationStatus.fromString((String) jsonMain.get("status"));
                code = (String) jsonMain.get("code");
                message = (String) jsonMain.get("message");

                Object jsonObject = jsonMain.get("content");

                JsonTreatment.doSomethingWithData(this, jsonObject);
            }
        }
    }

    /**
     * Met en pause le thread actuel le temps que la requête soit effectuée
     */
    public void sleepUntilFinished() {
        while (!loadingFinished && communicationAllowed) {
            try {
                Thread.sleep(1);
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
    public void run() {
        if (url == null) {
            // Si erreur d'URL
            System.err.println("Communication inutile, rien n'est effectué");
        }
        else {
            // Sinon si l'URL est bien construite, on vérifie la connexion

            if (requestToken == null) {
                // Si pas encore connecté
                send();
            }
            else {
                // Si déjà connecté

                if (typeOfCommunication == CHECK_CONNECTION || typeOfCommunication == UPDATE_CONNECTION) {
                    // Si l'on est actuellement en vérification ou en update, on envoie
                    send();
                }
                else {
                    // Sinon on vérifie le token

                    Communication checkComm = new CommunicationBuilder()
                            .startNow()
                            .sleepUntilFinished()
                            .checkConnection()
                            .build();

                    if (requestToken == null) {
                        // Si le token est périmé on le recrée

                        Communication updateComm = new CommunicationBuilder()
                                .startNow()
                                .sleepUntilFinished()
                                .updateConnection()
                                .build();

                        if (requestToken == null) {
                            // S'il n'est pas recréé, euh oups
                            System.err.println("help");
                        }
                        else {
                            // Si jeton recréé, on reprend

                            if (requestData.get("token") != null) {
                                requestData.remove("token");
                                requestData.put("token", requestToken);
                            }

                            send();
                        }
                    }
                    else {
                        // Si jeton valide, on reprend
                        send();
                    }
                }
            }
        }

        loadingFinished = true;

        setChanged();
        notifyObservers();
    }
}