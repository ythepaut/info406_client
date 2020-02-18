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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Observable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Communication, effectue les appels API
 * Les appels sont effectués en instance pour thread la connection et éviter de bloquer le thread courant
 *
 * Cette classe utilise le pattern Builder
 *
 * TODO: Communication comm = new Communication.CommunicationBuilder().startNow().sleepUntilFinished().connect("username", "password").build();
 * TODO: liste de projets
 * TODO: POST
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
 *      Communication comm = new Communication.CommunicationBuilder(true, true)
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
     * Codes réponse HTML
     */
    private static final int
            HTML_CUSTOM_DEFAULT_ERROR = -1,
            HTML_CUSTOM_TIMEOUT = 608,
            HTML_OK = 200,
            HTML_BAD_REQUEST = 400,
            HTML_UNAUTHORIZED = 401,
            HTML_FORBIDDEN = 403,
            HTML_NOT_FOUND = 404,
            HTML_TIMEOUT = 408;

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
     * Token, null si non connecté
     */
    private static String token = null;

    /**
     * Client en train de charger une ressource ou non
     */
    private static boolean isLoading = false;

    /**
     * Vérifie l'état de la connexion
     *
     * @return Connecté ou non
     */
    public static boolean isConnected() {
        return token != null;
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
        }

        /**
         * Constructeur envoyant tout de suite la requête à l'API
         *
         * @param startNow Lance le thread immédiatement ou non, si non il faudra lancer comm.start()
         */
        public CommunicationBuilder(boolean startNow) {
            this();
            this.startNow = startNow;
        }

        /**
         * Constructeur envoyant
         *
         * @param startNow Lance le thread
         * @param sleepUntilFinished Met le thread principal en pause tant que la requête n'est pas terminée
         */
        public CommunicationBuilder(boolean startNow, boolean sleepUntilFinished) {
            this(startNow);
            this.sleepUntilFinished = sleepUntilFinished;
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
            typeOfCommunication = CommunicationType.LOGIN;
            url = "auth?username=" + username + "&passwd=" + password;
            return this;
        }

        /**
         * Vérifie la connexion
         *
         * @return Builder non terminé avec URL
         */
        public CommunicationBuilder updateConnection() {
            typeOfCommunication = CommunicationType.UPDATE_CONNECTION;

            if (token == null) {
                url = "verify-token?token=null";
            }
            else {
                url = "verify-token?token=" + token;
            }

            return this;
        }

        public CommunicationBuilder getProjectList() {
            typeOfCommunication = CommunicationType.LIST_PROJECTS;

            if (token == null) {
                url = "project/list?token=null";
            }
            else {
                url = "project/list?token=" + token;
            }

            return this;
        }
    }

    private HashMap<Object, Object> results;

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
    private int htmlCode;

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
        results = new HashMap<>();
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
        return htmlCode;
    }

    /**
     * Méthode pour connecter l'instance à son URL
     */
    private void connectFromUrl() {
        // Requête API
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseApiUrl + url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
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
            htmlCode = response.statusCode();

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

                doSomethingWithData(jsonObject);
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
        while (!isFinished()) {
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
     * @param jsonObject Contenu à traiter
     */
    private synchronized void doSomethingWithData(Object jsonObject) {
        switch (typeOfCommunication) {
            case LOGIN:
                if (status.equals(STATUS_SUCCESS)) {
                    JSONObject jsonContent = (JSONObject) jsonObject;
                    token = (String) jsonContent.get("token");
                }
                break;

            case UPDATE_CONNECTION:
                if (htmlCode == HTML_OK) {
                    results.put("ok", true);
                }
                else if (htmlCode == HTML_UNAUTHORIZED) {
                    results.put("ok", false);
                }
                else {
                    results.put("ok", false);
                }
                break;

            case LIST_PROJECTS:
                JSONObject jsonContent = (JSONObject) jsonObject;
                JSONArray projects = (JSONArray) jsonContent.get("projects");

                for (Object projectObject : projects) {
                    JSONObject jsonProject = (JSONObject) projectObject;
                    // jsonProject.keys();
                }

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
            System.err.println("Communication inutile, rien n'est effectué");
        }
        else {
            connectFromUrl();
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