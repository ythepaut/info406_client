package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.utils.Location;
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
 * Les appels peuvent être effectués en statique, bloquant le thread courant,
 * ou en instance pour threadifier la connection
 *
 * @author Romain
 */
public class Communication extends Thread {
    /**
     * Client HTTP pour les requètes
     */
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    /**
     * Token, null si non connecté
     */
    private static String token = null;

    /**
     * Client en train de se connecter ou non
     */
    private static boolean isConnecting = false;

    /**
     * Client en train de charger une ressource ou non
     */
    private static boolean isLoading = false;

    /**
     * Client ayant finit son chargement ou non, utilise pour les instances threadifiées
     */
    private boolean loadingFinished;

    /**
     * Constructeur
     */
    public Communication() {
        super();

        this.loadingFinished = false;
    }

    /**
     * Connecte le client au serveur
     *
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @return Connecté ou non
     */
    public static boolean connect(String username, String password) {
        isConnecting = true;
        boolean connected;

        // Connexion
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.ythepaut.com/g4/actions/auth?username=" + username + "&passwd=" + password))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONParser parser = new JSONParser();
                Object parsedResponse = parser.parse(response.body());
                JSONObject jsonMain = (JSONObject) parsedResponse;

                if (jsonMain.get("status").equals("success")) {
                    JSONObject jsonContent = (JSONObject) jsonMain.get("content");

                    token = (String) jsonContent.get("token");
                    connected = true;
                } else {
                    // System.out.println(jsonMain.get("status"));
                    connected = false;
                }
            }
            else {
                connected = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            connected = false;
        }

        isConnecting = false;

        return connected;
    }

    /**
     * Vérification de la connexion via l'API, par instance
     *
     * @param checkOnline Vérifie via l'API ou non
     * @return Connecté ou non
     */
    @Deprecated
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
     * Vérifie l'état de la connexion
     *
     * @return Connecté ou non
     */
    public static boolean isConnected() {
        return token != null;
    }

    /**
     * En cours de connexion ou non
     *
     * @return En cours de connexion ou non
     */
    public static boolean isConnecting() {
        return isConnecting;
    }

    /**
     * En cours de chargement ou non
     *
     * @return En cours de chargement ou non
     */
    public static boolean isLoading() {
        return isConnecting || isLoading;
    }

    /**
     * Admin ou non
     *
     * @return Admin ou non
     */
    public static boolean isAdmin() {
        return true;
    }

    /**
     * Récupère les tâches
     *
     * @return Liste des tâches
     */
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
