package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.model.resource.human.HumanResourceList;
import fr.groupe4.clientprojet.model.task.Task;
import fr.groupe4.clientprojet.model.task.TaskList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.groupe4.clientprojet.communication.enums.CommunicationStatus;
import fr.groupe4.clientprojet.communication.enums.HTTPCode;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.message.Message;
import fr.groupe4.clientprojet.model.message.MessageList;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.project.ProjectList;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.human.User;
import fr.groupe4.clientprojet.model.timeslot.TimeSlot;
import fr.groupe4.clientprojet.model.timeslot.TimeSlotList;

/**
 * Traite le JSON de la classe Communication
 */
@SuppressWarnings("unused")
final class JsonTreatment {
    /**
     * Singleton pour que Communication puisse vérifier l'accès à ses token
     */
    private static final JsonTreatment singleton = new JsonTreatment();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructeur vide, utile seulement pour le singleton
     */
    private JsonTreatment() {}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fait quelque chose du contenu de la réponse de l'API
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    static void doSomethingWithData(Communication comm, Object jsonObject) {
        switch (comm.typeOfCommunication) {
            case LOGIN:
                login(comm, jsonObject);
                break;
            case UPDATE_CONNECTION:
                updateConnection(comm, jsonObject);
                break;

            case CREATE_PROJECT:
                break;
            case GET_PROJECT:
                getProject(comm, jsonObject);
                break;
            case LIST_PROJECTS:
                listProjects(comm, jsonObject);
                break;
            case ADD_RESOURCE_TO_PROJECT:
                addResourceToProject(comm, jsonObject);
                break;

            case GET_USER_INFOS:
                getUserInfos(comm, jsonObject);
                break;
            case GET_HUMAN_RESOURCE:
                getHumanResource(comm, jsonObject);
                break;
            case LIST_HUMAN_RESOURCE:
                listHumanResource(comm, jsonObject);
                break;

            case CREATE_TASK:
                break;
            case GET_TASK_LIST:
                getTaskList(comm, jsonObject);
                break;

            case ADD_TIME_SLOT:
                break;
            case GET_TIME_SLOT_LIST:
                getTimeSlotList(comm, jsonObject);
                break;

            case SEND_MESSAGE:
                break;
            case LIST_MESSAGES:
                listMessages(comm, jsonObject);
                break;

            default:
                Logger.error("Traitement JSON : type de communication non reconnu : " + comm.typeOfCommunication);
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Connexion
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void login(Communication comm, Object jsonObject) {
        if (comm.status.equals(CommunicationStatus.STATUS_SUCCESS)) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONObject jsonRequestToken = (JSONObject) jsonContent.get("requests-token");
            Communication.setRequestToken(singleton, (String) jsonRequestToken.get("value"));

            JSONObject jsonRenewToken = (JSONObject) jsonContent.get("renew-token");
            Communication.setRenewToken(singleton, (String) jsonRenewToken.get("value"));

            Communication.builder().getUserInfos().startNow().sleepUntilFinished().build();
        }
    }

    /**
     * Mise à jour de la connexion
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void updateConnection(Communication comm, Object jsonObject) {
        if (comm.HTTPCode == HTTPCode.HTTP_OK) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONObject jsonTokenContent = (JSONObject) jsonContent.get("requests-token");

            Communication.setRequestToken(singleton, (String) jsonTokenContent.get("value"));
        }
        else if (comm.HTTPCode == HTTPCode.HTTP_FORBIDDEN) {
            Logger.error("Update interdite !?");
        }
        else {
            Logger.error("Update malformée !?");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void getProject(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONObject jsonProject = (JSONObject) jsonContent.get("project");

            Project p = new Project(
                    (long) jsonProject.get("id"),
                    (String) jsonProject.get("name"),
                    (String) jsonProject.get("description"),
                    (long) jsonProject.get("deadline"),
                    (String) jsonProject.get("status")
            );

            comm.communicationResult = p;
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

        ProjectList projectsArray = new ProjectList();

        for (Object projectObject : projects) {
            JSONObject jsonProjectSet = (JSONObject) projectObject;
            Object[] keySet = jsonProjectSet.keySet().toArray();
            String key = String.valueOf(keySet[0]);

            JSONObject jsonProject = (JSONObject) jsonProjectSet.get(key);

            Project project = new Project(
                    (long) jsonProject.get("id"),
                    (String)jsonProject.get("name"),
                    (String) jsonProject.get("description"),
                    (long) jsonProject.get("deadline"),
                    (String) jsonProject.get("status"));

            projectsArray.add(project);
        }

        comm.communicationResult = projectsArray;
    }

    /**
     * Ajoute une ressource à un projet
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void addResourceToProject(Communication comm, Object jsonObject) {
        if (comm.getHTTPCode() != HTTPCode.HTTP_OK) {
            Logger.warning("L'ajout de ressource humaine a échoué");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Récupère les infos de l'utilisateur
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getUserInfos(Communication comm, Object jsonObject) {
        if (comm.HTTPCode == HTTPCode.HTTP_OK) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONObject jsonDataContent = (JSONObject) jsonContent.get("data");
            JSONObject jsonControlContent = (JSONObject) jsonDataContent.get("control");
            JSONObject jsonUserContent = (JSONObject) jsonDataContent.get("user");

            Communication c = Communication
                    .builder()
                    .startNow()
                    .sleepUntilFinished()
                    .getHumanResource((long) jsonUserContent.get("id_h_resource"))
                    .build();

            HumanResource humanResource = (HumanResource) c.getResult();

            comm.communicationResult = new User(humanResource,
                    (String) jsonControlContent.get("ip"),
                    (String) jsonControlContent.get("type"),
                    (long) jsonUserContent.get("id"),
                    (String) jsonUserContent.get("username"),
                    (String) jsonUserContent.get("email"));
        }
        else {
            Logger.error("Déconnecté en cours de route ?");
        }
    }

    /**
     * Récupère une ressource humaine
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getHumanResource(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            comm.communicationResult = new HumanResource(
                    (long) jsonContent.get("id"),
                    (String) jsonContent.get("firstname"),
                    (String) jsonContent.get("lastname"),
                    (String) jsonContent.get("job"),
                    (String) jsonContent.get("role"),
                    (String) jsonContent.get("description"));
        }
    }

    /**
     * Récupère la liste des ressources humaines
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listHumanResource(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONArray jsonArray = (JSONArray) jsonContent.get("h_ressources");

            HumanResourceList resourceList = new HumanResourceList();

            for (Object humanObject : jsonArray) {
                JSONObject jsonHumanSet = (JSONObject) humanObject;
                Object[] keySet = jsonHumanSet.keySet().toArray();
                String key = String.valueOf(keySet[0]);

                JSONObject jsonHuman = (JSONObject) jsonHumanSet.get(key);

                HumanResource human = new HumanResource(
                        (long) jsonHuman.get("id"),
                        (String) jsonHuman.get("firstname"),
                        (String) jsonHuman.get("lastname"),
                        (String) jsonHuman.get("job"),
                        (String) jsonHuman.get("role"),
                        (String) jsonHuman.get("description"));

                resourceList.add(human);
            }

            comm.communicationResult = resourceList;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Récupère la liste des tâches
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getTaskList(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONArray jsonTasks = (JSONArray) jsonContent.get("tasks");

            TaskList taskList = new TaskList();

            for (Object jsonTaskObject : jsonTasks) {
                JSONObject jsonTasksSet = (JSONObject) jsonTaskObject;

                Object[] keySet = jsonTasksSet.keySet().toArray();
                String key = String.valueOf(keySet[0]);

                JSONObject jsonTask = (JSONObject) jsonTasksSet.get(key);

                Task task = new Task(
                        (long) jsonTask.get("id"),
                        (String) jsonTask.get("name"),
                        (String) jsonTask.get("description"),
                        (String) jsonTask.get("status"),
                        (long) jsonTask.get("deadline"),
                        (long) jsonTask.get("project")
                );

                taskList.add(task);
            }

            comm.communicationResult = taskList;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Récupère une liste de créneaux
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getTimeSlotList(Communication comm, Object jsonObject) {
        if (comm.status.equals(CommunicationStatus.STATUS_SUCCESS)) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONArray jsonTimeSlots = (JSONArray) jsonContent.get("timeslots");

            TimeSlotList timeSlots = new TimeSlotList();

            for (Object jsonTimeSlotObject : jsonTimeSlots) {
                JSONObject jsonTimeSlotSet = (JSONObject) jsonTimeSlotObject;

                Object[] keySet = jsonTimeSlotSet.keySet().toArray();
                String key = String.valueOf(keySet[0]);

                JSONObject jsonTimeSlot = (JSONObject) jsonTimeSlotSet.get(key);

                TimeSlot timeSlot = new TimeSlot(
                        (long) jsonTimeSlot.get("id"),
                        (long) jsonTimeSlot.get("start"),
                        (long) jsonTimeSlot.get("end"),
                        (long) jsonTimeSlot.get("task"),
                        (long) jsonTimeSlot.get("room")
                );

                timeSlots.add(timeSlot);
            }

            comm.communicationResult = timeSlots;
        }
    }

    /**
     * Liste les messages de l'utilisateur
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listMessages(Communication comm, Object jsonObject) {
        if (comm.status.equals(CommunicationStatus.STATUS_SUCCESS)) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONArray jsonMessages = (JSONArray) jsonContent.get("messages");

            MessageList messages = new MessageList();

            for (Object jsonMessageObject : jsonMessages) {
                JSONObject jsonMessageSet = (JSONObject) jsonMessageObject;

                Object[] keySet = jsonMessageSet.keySet().toArray();
                String key = String.valueOf(keySet[0]);

                JSONObject jsonMessage = (JSONObject) jsonMessageSet.get(key);

                long sourceId = (long) jsonMessage.get("sourceId");

                Communication c = Communication
                        .builder()
                        .getHumanResource(sourceId)
                        .startNow()
                        .sleepUntilFinished()
                        .build();

                HumanResource humanResource = (HumanResource) c.getResult();

                Message message = new Message(
                        humanResource,
                        (long) jsonMessage.get("id"),
                        (long) jsonMessage.get("date"),
                        (long) jsonMessage.get("destinationId"),
                        (String) jsonMessage.get("destination"),
                        (String) jsonMessage.get("content")
                );

                messages.add(message);
            }

            comm.communicationResult = messages;
        }
    }
}
