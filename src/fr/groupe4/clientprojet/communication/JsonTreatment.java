package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.communication.enums.APICode;
import fr.groupe4.clientprojet.communication.enums.CommunicationStatus;
import fr.groupe4.clientprojet.communication.enums.HTTPCode;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.message.Message;
import fr.groupe4.clientprojet.model.message.MessageList;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.project.ProjectList;
import fr.groupe4.clientprojet.model.resource.human.*;
import fr.groupe4.clientprojet.model.resource.material.MaterialResource;
import fr.groupe4.clientprojet.model.resource.material.MaterialResourceList;
import fr.groupe4.clientprojet.model.resource.material.MaterialResourceProject;
import fr.groupe4.clientprojet.model.resource.material.MaterialResourceProjectList;
import fr.groupe4.clientprojet.model.task.Task;
import fr.groupe4.clientprojet.model.task.TaskList;
import fr.groupe4.clientprojet.model.timeslot.TimeSlot;
import fr.groupe4.clientprojet.model.timeslot.TimeSlotList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    private JsonTreatment() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fait quelque chose du contenu de la réponse de l'API
     *
     * @param comm       Communication à traiter
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
            case VERIFY_CONNECTION:
                verifyConnection(comm, jsonObject);
                break;

            case CREATE_PROJECT:
                debugError(comm, jsonObject);
                break;
            case GET_PROJECT:
                getProject(comm, jsonObject);
                break;
            case LIST_PROJECTS:
                listProjects(comm, jsonObject);
                break;
            case ADD_RESOURCE_TO_PROJECT:
                debugError(comm, jsonObject);
                break;
            case REMOVE_RESOURCE_FROM_PROJECT:
                debugError(comm, jsonObject);
                break;
            case LIST_USERS_FROM_PROJECT:
                listUsersFromProject(comm, jsonObject);
                break;
            case LIST_MATERIAL_FROM_PROJECT:
                listMaterialFromProject(comm, jsonObject);
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

            case CREATE_MATERIAL_RESOURCE:
                debugError(comm, jsonObject);
                break;
            case GET_MATERIAL_RESOURCE:
                getMaterialResource(comm, jsonObject);
                break;
            case LIST_MATERIAL_RESOURCE:
                listMaterialResource(comm, jsonObject);
                break;

            case CREATE_TASK:
                debugError(comm, jsonObject);
                break;
            case GET_TASK_LIST:
                getTaskList(comm, jsonObject);
                break;

            case ADD_TIME_SLOT:
                debugError(comm, jsonObject);
                break;
            case GET_TIME_SLOT_LIST:
                getTimeSlotList(comm, jsonObject);
                break;

            case SEND_MESSAGE:
                debugError(comm, jsonObject);
                break;
            case LIST_MESSAGES:
                listMessages(comm, jsonObject);
                break;

            case DEFAULT:
                Logger.warning("Case impossible ??");
                break;

            default:
                Logger.error("Traitement JSON : type de communication non reconnu : " + comm.typeOfCommunication);
                break;
        }
    }

    private static void debugError(Communication comm, Object jsonObject) {
        if (comm.getHTTPCode() != HTTPCode.HTTP_OK
                || comm.getCode() != APICode.SUCCESS
                || comm.getStatus() != CommunicationStatus.STATUS_SUCCESS) {

            Logger.warning("Erreur lors d'un appel serveur\nComm:", comm, "\nJSON:", jsonObject);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Connexion
     *
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void login(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
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
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void updateConnection(Communication comm, Object jsonObject) {
        if (comm.httpCode == HTTPCode.HTTP_OK) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONObject jsonTokenContent = (JSONObject) jsonContent.get("requests-token");

            Communication.setRequestToken(singleton, (String) jsonTokenContent.get("value"));
        } else if (comm.httpCode == HTTPCode.HTTP_FORBIDDEN) {
            Logger.error("Update interdite !?");
        } else {
            Logger.error("Update malformée !?");
        }
    }

    /**
     * Vérifie la validité d'une connexion
     *
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void verifyConnection(Communication comm, Object jsonObject) {
        if (comm.httpCode == HTTPCode.HTTP_OK) {
            comm.communicationResult = true;
        } else {
            comm.communicationResult = false;

            if (comm.httpCode != HTTPCode.HTTP_UNAUTHORIZED) {
                Logger.warning("Code réponse inconnu", comm, jsonObject);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void getProject(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONObject jsonProject = (JSONObject) jsonContent.get("project");

            comm.communicationResult = new Project(
                    (long) jsonProject.get("id"),
                    (String) jsonProject.get("name"),
                    (String) jsonProject.get("description"),
                    (long) jsonProject.get("deadline"),
                    (String) jsonProject.get("status")
            );
        }
    }

    /**
     * Liste les projets
     *
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listProjects(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
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
                        (String) jsonProject.get("name"),
                        (String) jsonProject.get("description"),
                        (long) jsonProject.get("deadline"),
                        (String) jsonProject.get("status"));

                projectsArray.add(project);
            }

            comm.communicationResult = projectsArray;
        }
    }

    /**
     * Liste les utilisateurs sur un projet
     *
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listUsersFromProject(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONArray jsonArrayHuman = (JSONArray) jsonContent.get("HUMAN");

            HumanResourceProjectList humanList = new HumanResourceProjectList();

            Communication[] comms = new Communication[jsonArrayHuman.size()];

            for (int i = 0; i < jsonArrayHuman.size(); i++) {
                JSONObject jsonHuman = (JSONObject) jsonArrayHuman.get(i);

                comms[i] = Communication.builder()
                        .startNow()
                        .getHumanResource(Long.parseLong((String) jsonHuman.get("id_resource")))
                        .build();
            }

            for (int i = 0; i < jsonArrayHuman.size(); i++) {
                JSONObject jsonHuman = (JSONObject) jsonArrayHuman.get(i);
                Communication c = comms[i];

                if (!c.isFinished()) {
                    c.sleepUntilFinished();
                }

                HumanResource humanResource = (HumanResource) c.getResult();

                if (humanResource == null) {
                    Logger.error("Ressource humaine nulle");
                } else {
                    HumanResourceProject human = new HumanResourceProject(
                            humanResource,
                            Long.parseLong((String) jsonHuman.get("id_project")),
                            Long.parseLong((String) jsonHuman.get("date_start")),
                            Long.parseLong((String) jsonHuman.get("date_end")),
                            Long.parseLong((String) jsonHuman.get("id_issuer")),
                            (String) jsonHuman.get("status"),
                            Long.parseLong((String) jsonHuman.get("id"))
                    );

                    humanList.add(human);
                }
            }

            comm.communicationResult = humanList;
        }
    }

    /**
     * Liste le matériel alloué à un projet
     *
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listMaterialFromProject(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONArray jsonArrayMaterial = (JSONArray) jsonContent.get("MATERIAL");

            MaterialResourceProjectList materialList = new MaterialResourceProjectList();

            Communication[] comms = new Communication[jsonArrayMaterial.size()];

            for (int i = 0; i < jsonArrayMaterial.size(); i++) {
                JSONObject jsonMaterial = (JSONObject) jsonArrayMaterial.get(i);

                comms[i] = Communication.builder()
                        .startNow()
                        .getMaterialResource(Long.parseLong((String) jsonMaterial.get("id_resource")))
                        .build();
            }

            for (int i = 0; i < jsonArrayMaterial.size(); i++) {
                JSONObject jsonMaterial = (JSONObject) jsonArrayMaterial.get(i);
                Communication c = comms[i];

                if (!c.isFinished()) {
                    c.sleepUntilFinished();
                }

                MaterialResource materialResource = (MaterialResource) c.getResult();

                if (materialResource == null) {
                    Logger.error("Ressource matérielle nulle");
                } else {
                    MaterialResourceProject material = new MaterialResourceProject(
                            materialResource,
                            Long.parseLong((String) jsonMaterial.get("id_project")),
                            Long.parseLong((String) jsonMaterial.get("date_start")),
                            Long.parseLong((String) jsonMaterial.get("date_end")),
                            Long.parseLong((String) jsonMaterial.get("id_issuer")),
                            (String) jsonMaterial.get("status"),
                            Long.parseLong((String) jsonMaterial.get("id"))
                    );

                    materialList.add(material);
                }
            }

            comm.communicationResult = materialList;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Récupère les infos de l'utilisateur
     *
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getUserInfos(Communication comm, Object jsonObject) {
        if (comm.httpCode == HTTPCode.HTTP_OK) {
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

            if (humanResource == null) {
                Logger.error("humanResource null ??", c);
            } else {
                User.initUser(
                        humanResource,
                        (String) jsonControlContent.get("ip"),
                        (String) jsonControlContent.get("type"),
                        (long) jsonUserContent.get("id"),
                        (String) jsonUserContent.get("username"),
                        (String) jsonUserContent.get("email"));

                comm.communicationResult = User.getUser();
            }
        } else {
            Logger.error("Déconnecté en cours de route ?");
        }
    }

    /**
     * Récupère une ressource humaine
     *
     * @param comm       Communication à traiter
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
     * @param comm       Communication à traiter
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
     * Récupère une ressource matérielle
     *
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getMaterialResource(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            comm.communicationResult = new MaterialResource(
                    (long) jsonContent.get("id"),
                    (String) jsonContent.get("name"),
                    (String) jsonContent.get("description")
            );
        }
    }

    /**
     * Liste les ressources matérielles
     *
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listMaterialResource(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONArray jsonArray = (JSONArray) jsonContent.get("m_ressources");

            MaterialResourceList resourceList = new MaterialResourceList();

            for (Object materialObject : jsonArray) {
                JSONObject jsonMaterialSet = (JSONObject) materialObject;
                Object[] keySet = jsonMaterialSet.keySet().toArray();
                String key = String.valueOf(keySet[0]);

                JSONObject jsonMaterial = (JSONObject) jsonMaterialSet.get(key);

                MaterialResource material = new MaterialResource(
                        (long) jsonMaterial.get("id"),
                        (String) jsonMaterial.get("name"),
                        (String) jsonMaterial.get("description"));

                resourceList.add(material);
            }

            comm.communicationResult = resourceList;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Récupère la liste des tâches
     *
     * @param comm       Communication à traiter
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
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getTimeSlotList(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
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
     * @param comm       Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listMessages(Communication comm, Object jsonObject) {
        if (comm.status == CommunicationStatus.STATUS_SUCCESS) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONArray jsonMessages = (JSONArray) jsonContent.get("messages");

            MessageList messages = new MessageList();

            Communication[] comms = new Communication[jsonMessages.size()];

            for (int i = 0; i < jsonMessages.size(); i++) {
                JSONObject jsonMessageSet = (JSONObject) jsonMessages.get(i);

                Object[] keySet = jsonMessageSet.keySet().toArray();
                String key = String.valueOf(keySet[0]);

                JSONObject jsonMessage = (JSONObject) jsonMessageSet.get(key);

                long sourceId = (long) jsonMessage.get("sourceId");

                comms[i] = Communication
                        .builder()
                        .getHumanResource(sourceId)
                        .startNow()
                        .sleepUntilFinished()
                        .build();
            }

            for (int i = 0; i < jsonMessages.size(); i++) {
                JSONObject jsonMessageSet = (JSONObject) jsonMessages.get(i);

                Object[] keySet = jsonMessageSet.keySet().toArray();
                String key = String.valueOf(keySet[0]);

                JSONObject jsonMessage = (JSONObject) jsonMessageSet.get(key);

                Communication c = comms[i];

                if (!c.isFinished()) {
                    c.sleepUntilFinished();
                }

                HumanResource humanResource = (HumanResource) c.getResult();

                if (humanResource == null) {
                    Logger.error("humanResource null ??", c);
                } else {
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
            }

            comm.communicationResult = messages;
        }
    }
}
