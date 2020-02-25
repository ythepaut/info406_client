package fr.groupe4.clientprojet.communication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static fr.groupe4.clientprojet.communication.enums.CommunicationStatus.*;
import static fr.groupe4.clientprojet.communication.enums.HTMLCode.*;

/**
 * Traite le JSON de la classe Communication
 */
class JsonTreatment {
    /**
     * Fait quelque chose du contenu de la réponse de l'API <br>
     * synchronized pour éviter les conflits de threads lors de plusieurs traitements simultanés
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    protected static synchronized void doSomethingWithData(Communication comm, Object jsonObject) {
        switch (comm.typeOfCommunication) {
            case LOGIN:
                login(comm, jsonObject);
                break;

            case CHECK_CONNECTION:
                checkConnection(comm, jsonObject);
                break;

            case UPDATE_CONNECTION:
                updateConnection(comm, jsonObject);
                break;

            case LIST_PROJECTS:
                listProjects(comm, jsonObject);
                break;

            default:
                System.err.println("Type de communication non reconnu");
                break;
        }
    }

    /**
     * Connexion
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void login(Communication comm, Object jsonObject) {
        if (comm.status.equals(STATUS_SUCCESS)) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONObject jsonRequestToken = (JSONObject) jsonContent.get("requests-token");
            Communication.requestToken = (String) jsonRequestToken.get("value");

            JSONObject jsonRenewToken = (JSONObject) jsonContent.get("renew-token");
            Communication.renewToken = (String) jsonRenewToken.get("value");
        }
    }

    /**
     * Vérification de la connexion
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void checkConnection(Communication comm, Object jsonObject) {
        if (comm.htmlCode == HTML_UNAUTHORIZED) {
            Communication.requestToken = null;
        }
        else {
            if (comm.htmlCode == HTML_OK) {
                // Tout va bien
            }
            else {
                System.err.println("Réponse inconnue");
            }
        }
    }

    /**
     * Mise à jour de la connexion
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void updateConnection(Communication comm, Object jsonObject) {
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
    }

    /**
     * Liste les projets
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listProjects(Communication comm, Object jsonObject) {
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
    }
}
