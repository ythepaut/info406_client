package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.utils.Location;

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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Communication, effectue les appels API
 * Les appels sont effectués en instance pour thread la connection et éviter de bloquer le thread courant
 *
 * TODO: timeout
 * TODO: connectFromUrl erreur
 *
 * Exemple d'utilisation :
 *
 *      Communication c = new Communication().connect("username", "password").build();
 *      c.start();
 *      c.sleepUntilFinished();
 *
 * OU
 *
 *      Communication c = new Communication(true, true).connect("username", "password").build();
 *
 * System.out.println(c.getStatus()); // success
 * System.out.println(c.getCode()); // SUCCESS_AUTHENTICATED
 * System.out.println(c.getMessage()); // Authentication successful and JWT generated.
 * System.out.println(c.getHtmlCode()); // 200
 * System.out.println(Communication.isConnected()); // true
 *
 * @author Romain
 */
public class Communication extends Thread {
    /**
     * Client HTTP pour les requètes
     */
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    /**
     * URL de l'API
     */
    private static final String baseApiUrl = "https://api.ythepaut.com/g4/actions/";

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
     * En cours de chargement ou non
     *
     * @return En cours de chargement ou non
     */
    public static boolean isLoading() {
        return isLoading;
    }

    /**
     * Admin ou non
     *
     * TODO
     *
     * @return Admin ou non
     */
    public static boolean isAdmin() {
        return true;
    }

    /**
     * Client ayant finit son chargement ou non, utilise pour les instances thread
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
     * Constructeur bien formé ou non
     */
    private boolean isWellFormed;

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
     * Constructeur de base
     */
    public Communication() {
        isWellFormed = false;
        startNow = false;
        sleepUntilFinished = false;
    }

    /**
     * Constructeur
     *
     * @param startNow Lance le thread immédiatement ou non, si non il faudra lancer comm.start()
     */
    public Communication(boolean startNow) {
        this();
        this.startNow = startNow;
    }

    /**
     * Constructeur
     *
     * @param startNow Lance le thread
     * @param sleepUntilFinished Met le thread principal en pause tant que la requête n'est pas terminée
     */
    public Communication(boolean startNow, boolean sleepUntilFinished) {
        this(startNow);
        this.sleepUntilFinished = sleepUntilFinished;
    }

    /**
     * Builder final
     *
     * @return Communication bien formée
     */
    public Communication build() {
        isWellFormed = true;

        if (startNow) {
            start();

            if (sleepUntilFinished) {
                sleepUntilFinished();
            }
        }

        return this;
    }

    /**
     * Connecte le client au serveur
     *
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     *
     * @return Communication avec URL
     */
    public Communication connect(String username, String password) {
        typeOfCommunication = CommunicationType.LOGIN;
        url = "auth?username=" + username + "&passwd=" + password;
        return this;
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
                .build();

        HttpResponse<String> response = null;
        // Réponse de l'API

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException e) {
            System.err.println("URL inconnue");
        }
        catch (InterruptedException e) {
            System.err.println("Requête interrompue");
        }

        if (response != null) {
            htmlCode = response.statusCode();

            JSONParser parser = new JSONParser();
            Object parsedResponse = null;

            try {
                parsedResponse = parser.parse(response.body());
            }
            catch (ParseException e) {
                System.err.println("Réponse invalide");
            }

            if (parsedResponse != null) {
                JSONObject jsonMain = (JSONObject) parsedResponse;

                status = (String) jsonMain.get("status");
                code = (String) jsonMain.get("code");
                message = (String) jsonMain.get("message");

                Object jsonObject = jsonMain.get("content");

                try {
                    JSONObject jsonContent = (JSONObject) jsonObject;
                    doSomethingWithData(jsonContent);
                }
                catch (Exception e) {
                    System.err.println("hmmm");
                }
            }
        }

        loadingFinished = true;
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
        while (!isFinished()  ) {
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
     * @param jsonContent Contenu à traiter
     */
    private void doSomethingWithData(JSONObject jsonContent) {
        switch (typeOfCommunication) {
            case LOGIN:
                if (status.equals("success")) {
                    token = (String) jsonContent.get("token");
                }
                else {
                    // Identifiants invalides
                }
                break;

            default:
                break;
        }
    }

    /**
     * Lance le thread de connexion à l'API
     */
    @Override
    public void run() {
        if (isWellFormed) {
            connectFromUrl();
        }
        else {
            System.err.println("Connexion mal formée");
        }
    }

    /**
     * Vérification de la connexion via l'API, par instance
     *
     * @param checkOnline Vérifie via l'API ou non
     * @return Connecté ou non
     */
    public boolean isConnected(boolean checkOnline) {
        boolean connected = false;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.ythepaut.com/g4/actions/verify-token?token=" + token))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            Object parsedResponse = parser.parse(response.body());
            JSONObject jsonMain = (JSONObject) parsedResponse;

            if (jsonMain.get("status") == "200") {
                connected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connected;
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
            File fileXml = new File(Location.getPath() + "/fr/groupe4/clientprojet/data/XML/calendar.xml");
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