package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.utils.Location;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static fr.groupe4.clientprojet.communication.CommunicationType.*;
import static fr.groupe4.clientprojet.communication.HTMLCode.*;

/**
 * Communication, effectue les appels API
 * Les appels sont effectués en instance pour thread la connection et éviter de bloquer le thread courant
 *
 * Cette classe utilise le pattern Builder
 *
 * TODO: JWT.io
 * TODO: fonction qui génère l'URL
 *
 * Exemple d'utilisation :
 *      Communication comm = new Communication.CommunicationBuilder()
 *                                            .connect("username", "password")
 *                                            .build();
 *      comm.start();
 *      comm.sleepUntilFinished();
 *
 * Autre exemple :
 *      Communication comm = new Communication.CommunicationBuilder()
 *                                            .startNow()
 *                                            .sleepUntilFinished()
 *                                            .connect("username", "password")
 *                                            .build();
 *
 * Résultats de la connexion :
 *      comm.getStatus(); // success
 *      comm.getCode(); // SUCCESS_AUTHENTICATED
 *      comm.getMessage(); // Authentication successful and JWT generated.
 *      comm.getHtmlCode(); // 200
 *      Communication.isConnected(); // true
 *
 * @author Romain
 */
public class Communication extends Observable implements Runnable {
    /**
     * Client HTTP pour les requètes
     */
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    /**
     * URL de l'API
     */
    private static final String baseApiUrl = "https://api.ythepaut.com/g4/actions/";

    /**
     * Statut de la réponse API
     */
    private static final String
            STATUS_SUCCESS = "success",
            STATUS_ERROR = "error";

    /**
     * Temps avant de timeout
     */
    private static final Duration TIMEOUT_DELAY = Duration.ofSeconds(30);

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

    /**
     * Vérifie l'état de la connexion
     *
     * @return Connecté ou non
     */
    public static boolean isConnected() {
        return requestToken != null;
    }

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
    public static class CommunicationBuilder {
        /**
         * Type de communication
         */
        private CommunicationType typeOfCommunication;

        /**
         * URL à envoyer à l'API
         */
        private String url = null;

        /**
         * Se lance tout de suite après le constructeur ou nécessite un comm.start()
         */
        private boolean startNow;

        /**
         * Attend que la requête soit terminée et bloque le thread
         * Cette variable ne sert que si startNow est à true
         */
        private boolean sleepUntilFinished;

        /**
         * Constructeur n'envoyant pas automatiquement l'appel API
         */
        public CommunicationBuilder() {
            startNow = false;
            sleepUntilFinished = false;
            requestData = new HashMap<>();
        }

        private HashMap<String, String> requestData;;

        /**
         * Lance la communication tout de suite
         *
         * @return Reste du builder
         */
        public CommunicationBuilder startNow() {
            startNow = true;
            return this;
        }

        /**
         * Attend que la communication soit terminée
         *
         * @return Reste du builder
         */
        public CommunicationBuilder sleepUntilFinished() {
            sleepUntilFinished = true;
            return this;
        }

        /**
         * Builder final
         *
         * @return Communication bien formée
         */
        public Communication build() {
            return new Communication(this);
        }

        /**
         * Connecte le client au serveur
         *
         * @param username Nom d'utilisateur
         * @param password Mot de passe
         *
         * @return Builder non terminé avec URL
         */
        public CommunicationBuilder connect(String username, String password) {
            typeOfCommunication = LOGIN;
            url = "auth/connect";
            requestData.put("username", username);
            requestData.put("passwd", password);
            return this;
        }

        public CommunicationBuilder checkConnection() {
            typeOfCommunication = CHECK_CONNECTION;
            url = "auth/verify";
            requestData.put("token", requestToken);
            return this;
        }

        /**
         * Vérifie la connexion
         *
         * @return Builder non terminé avec URL
         */
        public CommunicationBuilder updateConnection() {
            typeOfCommunication = UPDATE_CONNECTION;
            url = "auth/renew";
            requestData.put("token", renewToken);
            return this;
        }

        public CommunicationBuilder getProjectList() {
            typeOfCommunication = LIST_PROJECTS;

            url = "project/list";

            requestData.put("token", requestToken);

            return this;
        }
    }

    /**
     * Data de la requête
     */
    private HashMap<String, String> requestData;

    private Object communicationResult;

    /**
     * Client ayant finit son chargement ou non
     */
    private boolean loadingFinished;

    /**
     * Type de communication
     */
    private CommunicationType typeOfCommunication;

    /**
     * URL à envoyer à l'API
     */
    private String url;

    /**
     * Statut de la requête, comme "success" ou "error"
     */
    private String status;

    /**
     * Code de l'API, par exemple "ERROR_INVALID_USER_CREDENTIALS" ou "SUCCESS_AUTHENTICATED"
     */
    private String code;

    /**
     * Code HTML de la requête, comme un code 200 (OK) ou 404 (Not Found)
     */
    private HTMLCode htmlCode;

    /**
     * Message associé à la requête
     */
    private String message;

    /**
     * Constructeur de la Communication
     *
     * @param builder Builder de la communication
     */
    private Communication(CommunicationBuilder builder) {
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
        return status;
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

    public Object getResult() {
        return communicationResult;
    }

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

                status = (String) jsonMain.get("status");
                code = (String) jsonMain.get("code");
                message = (String) jsonMain.get("message");

                Object jsonObject = jsonMain.get("content");

                doSomethingWithData(this, jsonObject);
            }
        }
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
     * Fait quelque chose du contenu de la réponse de l'API
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static synchronized void doSomethingWithData(Communication comm, Object jsonObject) {
        switch (comm.typeOfCommunication) {
            case LOGIN:
                if (comm.status.equals(STATUS_SUCCESS)) {
                    JSONObject jsonContent = (JSONObject) jsonObject;

                    JSONObject jsonRequestToken = (JSONObject) jsonContent.get("requests-token");
                    requestToken = (String) jsonRequestToken.get("value");

                    JSONObject jsonRenewToken = (JSONObject) jsonContent.get("renew-token");
                    renewToken = (String) jsonRenewToken.get("value");
                }
                break;

            case CHECK_CONNECTION:
                if (comm.htmlCode == HTML_UNAUTHORIZED) {
                    requestToken = null;
                }
                else {
                    if (comm.htmlCode == HTML_OK) {
                        // Tout va bien
                    }
                    else {
                        System.err.println("Réponse inconnue");
                    }
                }
                break;

            case UPDATE_CONNECTION:
                if (comm.htmlCode == HTML_OK) {
                    JSONObject jsonContent = (JSONObject) jsonObject;

                    System.out.println(jsonContent.toString());
                }
                else if (comm.htmlCode == HTML_FORBIDDEN) {
                    System.err.println("Update interdite !?");
                }
                else {
                    System.err.println("Update malformée !?");
                }
                break;

            case LIST_PROJECTS:
                JSONObject jsonContent = (JSONObject) jsonObject;
                JSONArray projects = (JSONArray) jsonContent.get("projects");

                ArrayList<HashMap<String, Object>> projectsArray = new ArrayList<>();

                for (Object projectObject : projects) {
                    JSONObject jsonProjectSet = (JSONObject) projectObject;
                    Object[] keySet = jsonProjectSet.keySet().toArray();
                    String key = String.valueOf(keySet[0]);

                    JSONObject jsonProject = (JSONObject) jsonProjectSet.get(key);

                    HashMap<String, Object> projectArray = new HashMap<>();

                    projectArray.put("id", jsonProject.get("id"));
                    projectArray.put("name", jsonProject.get("name"));
                    projectArray.put("description", jsonProject.get("description"));
                    projectArray.put("deadline", jsonProject.get("deadline"));
                    projectArray.put("status", jsonProject.get("status"));

                    projectsArray.add(projectArray);
                }

                comm.communicationResult = projectsArray;

                break;

            default:
                System.err.println("Type de communication non reconnu");
                break;
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

                    Communication checkComm = new Communication.CommunicationBuilder()
                            .startNow()
                            .sleepUntilFinished()
                            .checkConnection()
                            .build();

                    if (requestToken == null) {
                        // Si le token est périmé on le recrée

                        Communication updateComm = new Communication.CommunicationBuilder()
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

    /**
     * Récupère les tâches
     *
     * @deprecated À transformer en instance
     *
     * @return Liste des tâches
     */
    @Deprecated
    public static TaskList getWeekTasks(int week, int year) throws IOException {
        if (!isConnected()) {
            throw new IOException("Non connecté");
        }

        TaskList tasks = new TaskList();

        try {
            File fileXml = new File(Location.getPath() + "/data/XML/calendar.xml");
            // Récupération du XML

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileXml);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("calendar");
            // Liste des nodes XML

            for (int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // Création de la tâche à partir du node actuel

                    Element element = (Element) node;

                    Task task = new Task(element.getAttribute("id"));

                    task.setDescription(element.getElementsByTagName("description").item(0).getTextContent());

                    tasks.add(task);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }
}