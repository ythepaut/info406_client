package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.communication.enums.CommunicationType;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.message.enums.MessageResource;
import fr.groupe4.clientprojet.model.project.enums.ProjectStatus;
import fr.groupe4.clientprojet.model.resource.ResourceType;
import fr.groupe4.clientprojet.model.resource.human.User;
import fr.groupe4.clientprojet.model.task.enums.TaskStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Objects;

/**
 * Builder de la communication <br>
 * <br>
 * Les variables ne sont accessibles qu'au package <br>
 * <br>
 * Cf. Communication pour voir un exemple
 *
 * @see Communication
 */
@SuppressWarnings("unused")
public final class CommunicationBuilder {
    /**
     * Transforme une variable Temporal en seconde
     *
     * @param time      LocalDate ou LocalDateTime, à transformer en secondes
     * @param allowNull Si true, null renverra 0
     * @return Temps en secondes
     * @throws IllegalArgumentException Si not allowNull et time == null, ou si time n'est ni LocalDate ni LocalDateTime
     */
    private static long temporalToSeconds(@Nullable Temporal time, boolean allowNull) throws IllegalArgumentException {
        long sec;

        if (time == null) {
            if (allowNull) {
                sec = 0;
            } else {
                throw new IllegalArgumentException("Temps null");
            }
        } else {
            if (time instanceof LocalDate) {
                sec = ((LocalDate) time).atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
            } else if (time instanceof LocalDateTime) {
                sec = ((LocalDateTime) time).atZone(ZoneId.systemDefault()).toEpochSecond();
            } else {
                throw new IllegalArgumentException("Type invalide");
            }
        }

        return sec;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Type de communication
     */
    @NotNull
    CommunicationType typeOfCommunication;

    /**
     * Se lance tout de suite après le constructeur ou nécessite un comm.start()
     */
    boolean startNow;

    /**
     * Laisse tourner en daemon
     */
    boolean keepAlive;

    /**
     * Attend que la requête soit terminée et bloque le thread <br>
     * Cette variable ne sert que si startNow est à true
     */
    boolean sleepUntilFinished;

    /**
     * Data à envoyer en POST pour la requête
     */
    @NotNull
    HashMap<String, Object> requestData;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructeur
     */
    public CommunicationBuilder() {
        startNow = false;
        keepAlive = false;
        sleepUntilFinished = false;
        requestData = new HashMap<>();
        typeOfCommunication = CommunicationType.DEFAULT;
    }

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
     * Reste actif ou non
     *
     * @return Reste du builder
     */
    CommunicationBuilder keepAlive() {
        keepAlive = true;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Connecte le client au serveur
     *
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder connect(@NotNull String username,
                                        @NotNull String password) {
        typeOfCommunication = CommunicationType.LOGIN;
        requestData.put("username", username);
        requestData.put("passwd", password);
        return this;
    }

    /**
     * Actualise la connexion
     *
     * @return Builder non terminé avec URL
     */
    CommunicationBuilder updateConnection() {
        typeOfCommunication = CommunicationType.UPDATE_CONNECTION;
        requestData.put("token", Communication.getRenewToken(this));
        return this;
    }

    /**
     * Vérifie une connexion existante
     *
     * @return Builder non terminé avec URL
     */
    CommunicationBuilder verifyConnection() {
        typeOfCommunication = CommunicationType.VERIFY_CONNECTION;
        requestData.put("token", Communication.getRenewToken(this));
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Crée un nouveau projet
     * Cf. getTaskList pour exemple détaillé de l'utilisation de Temporal
     *
     * @param name        Nom du projet
     * @param description Description du projet
     * @param deadline    Date limite du projet : LocalDate ou LocalDateTime, null sera traité comme sans date limite
     * @param status      État du projet
     * @return Builder non terminé avec URL
     * @see ProjectStatus
     * @see #getTaskList
     */
    public CommunicationBuilder createProject(@NotNull String name,
                                              @NotNull String description,
                                              @Nullable Temporal deadline,
                                              @NotNull ProjectStatus status) {
        long deadlineSecond = temporalToSeconds(deadline, true);
        typeOfCommunication = CommunicationType.CREATE_PROJECT;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("name", name);
        requestData.put("description", description);
        requestData.put("deadline", deadlineSecond);
        requestData.put("status", status.toString());
        return this;
    }

    /**
     * Récupère un projet
     *
     * @param id Id du projet
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getProject(long id) {
        typeOfCommunication = CommunicationType.GET_PROJECT;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("id", id);
        return this;
    }

    /**
     * Récupère la liste des projets
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getProjectList() {
        typeOfCommunication = CommunicationType.LIST_PROJECTS;
        requestData.put("token", Communication.getRequestToken(this));
        return this;
    }

    /**
     * Ajoute une ressource à un projet
     *
     * @param projectId  Id du projet
     * @param type       Type de la ressource
     * @param resourceId Id de la ressource
     * @param start      Date de début, maintenant si null
     * @param end        Date de fin, pas de fin si null
     * @return Builder non terminé avec URL
     * @see #getUserTimeSlotList Exemple de Temporal
     */
    public CommunicationBuilder addResourceToProject(long projectId,
                                                     @NotNull ResourceType type,
                                                     long resourceId,
                                                     @Nullable Temporal start,
                                                     @Nullable Temporal end) {
        long t2 = temporalToSeconds(end, true);

        if (t2 == 0) {
            Logger.warning("Date de fin null, sera sûrement changé plus tard");
            t2 = Long.MAX_VALUE;
        }

        typeOfCommunication = CommunicationType.ADD_RESOURCE_TO_PROJECT;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("project", projectId);
        requestData.put("type", type.getNameForAPI());
        requestData.put("id", resourceId);

        if (start != null) {
            long t1 = temporalToSeconds(start, false);
            requestData.put("start", t1);
        }

        requestData.put("end", t2);
        return this;
    }

    /**
     * Ajoute une ressource matérielle à un projet
     *
     * @param projectId  Id du projet
     * @param materialId Id de la ressource matérielle
     * @param start      Date de début, maintenant si null
     * @param end        Date de fin, pas de fin si null
     * @return Builder non terminé avec URL
     * @see #getUserTimeSlotList Exemple de Temporal
     */
    public CommunicationBuilder addMaterialResourceToProject(long projectId,
                                                             long materialId,
                                                             @Nullable Temporal start,
                                                             @Nullable Temporal end) {
        return addResourceToProject(projectId, ResourceType.MATERIAL_RESOURCE, materialId, start, end);
    }

    /**
     * Ajoute une ressource humaine à un projet
     *
     * @param projectId Id du projet
     * @param humanId   Id de la ressource humaine
     * @param start     Date de début, maintenant si null
     * @param end       Date de fin, pas de fin si null
     * @return Builder non terminé avec URL
     * @see #getUserTimeSlotList Exemple de Temporal
     */
    public CommunicationBuilder addHumanResourceToProject(long projectId,
                                                          long humanId,
                                                          @Nullable Temporal start,
                                                          @Nullable Temporal end) {
        return addResourceToProject(projectId, ResourceType.HUMAN_RESOURCE, humanId, start, end);
    }

    /**
     * Retire une ressource d'un projet
     *
     * @param projectId  Id du projet
     * @param type       Type de ressource
     * @param resourceId Id de la ressource
     * @param time       Date comprise entre la date d'allocation et la deadline
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder removeResourceFromProject(long projectId,
                                                          @NotNull ResourceType type,
                                                          long resourceId,
                                                          @NotNull Temporal time) {
        typeOfCommunication = CommunicationType.REMOVE_RESOURCE_FROM_PROJECT;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("project", projectId);
        requestData.put("type", type.getNameForAPI());
        requestData.put("id", resourceId);
        long t = temporalToSeconds(time, false);
        requestData.put("date", t);
        return this;
    }

    /**
     * Retire une ressource matérielle d'un projet
     *
     * @param projectId  Id du projet
     * @param materialId Id de la ressource matérielle
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder removeMaterialResourceFromProject(long projectId,
                                                                  long materialId) {
        return removeResourceFromProject(projectId, ResourceType.MATERIAL_RESOURCE, materialId, LocalDateTime.now());
    }

    /**
     * Retire une ressource humaine d'un projet
     *
     * @param projectId Id du projet
     * @param humanId   Id de la ressource humaine
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder removeHumanResourceFromProject(long projectId,
                                                               long humanId) {
        return removeResourceFromProject(projectId, ResourceType.HUMAN_RESOURCE, humanId, LocalDateTime.now());
    }

    /**
     * Liste les utilisateurs associés à un projet
     *
     * @param projectId Id du projet
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder listUsersFromProject(long projectId) {
        typeOfCommunication = CommunicationType.LIST_USERS_FROM_PROJECT;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("project", projectId);
        long t = temporalToSeconds(LocalDateTime.now(), false);
        requestData.put("date", t);
        return this;
    }

    /**
     * Liste les ressources matérielles associées à un projet
     *
     * @param projectId Id du projet
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder listMaterialFromProject(long projectId) {
        typeOfCommunication = CommunicationType.LIST_MATERIAL_FROM_PROJECT;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("project", projectId);
        long t = temporalToSeconds(LocalDateTime.now(), false);
        requestData.put("date", t);
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Récupère les infos de l'utilisateur
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getUserInfos() {
        typeOfCommunication = CommunicationType.GET_USER_INFOS;
        requestData.put("token", Communication.getRequestToken(this));
        return this;
    }

    /**
     * Récupère une ressource humaine
     *
     * @param id Id de la ressource humaine
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getHumanResource(long id) {
        typeOfCommunication = CommunicationType.GET_HUMAN_RESOURCE;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("id", id);
        return this;
    }

    /**
     * Récupère la liste des ressources humaines
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getHumanResourceList() {
        typeOfCommunication = CommunicationType.LIST_HUMAN_RESOURCE;
        requestData.put("token", Communication.getRequestToken(this));
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Crée une ressource matérielle
     *
     * @param name        Nom de la ressource matérielle
     * @param description Description (chaine vide si null)
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder createMaterialResource(@NotNull String name, @Nullable String description) {
        typeOfCommunication = CommunicationType.CREATE_MATERIAL_RESOURCE;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("name", name);
        requestData.put("description", Objects.requireNonNullElse(description, ""));
        return this;
    }

    /**
     * Récupère une ressource matérielle
     *
     * @param id Id de la ressource matérielle
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getMaterialResource(long id) {
        typeOfCommunication = CommunicationType.GET_MATERIAL_RESOURCE;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("id", id);
        return this;
    }

    /**
     * Liste les ressources matérielle
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder listMaterialResource() {
        typeOfCommunication = CommunicationType.LIST_MATERIAL_RESOURCE;
        requestData.put("token", Communication.getRequestToken(this));
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Crée une tâche <br>
     * Cf. getUserTimeSlotList pour exemple détaillé de l'utilisation de Temporal
     *
     * @param name        Nom de la tâche
     * @param description Description de la tâche
     * @param status      État de la tâche
     * @param deadline    Date limite de la tâche : LocalDate ou LocalDateTime, null sera traité comme sans date limite
     * @param projectId   Id du projet parent
     * @return Builder non terminé avec URL
     * @see TaskStatus
     * @see #getUserTimeSlotList
     */
    public CommunicationBuilder createTask(@NotNull String name,
                                           @NotNull String description,
                                           @NotNull TaskStatus status,
                                           @Nullable Temporal deadline,
                                           long projectId) {
        long t = temporalToSeconds(deadline, true);
        typeOfCommunication = CommunicationType.CREATE_TASK;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("name", name);
        requestData.put("description", description);
        requestData.put("status", status.toString());
        requestData.put("deadline", t);
        requestData.put("project", projectId);
        return this;
    }

    /**
     * Récupère la liste des tâches d'un projet
     *
     * @param projectId Id du projet parent
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getTaskList(long projectId) {
        typeOfCommunication = CommunicationType.GET_TASK_LIST;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("project", projectId);
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Ajoute un créneau <br>
     * Cf. getTaskList pour exemple détaillé de l'utilisation de Temporal
     *
     * @param from   Date de début
     * @param to     Date de fin
     * @param taskId Id de la tâche parente
     * @param roomId Id de la salle du créneau
     * @return Builder non terminé avec URL
     * @see #getUserTimeSlotList
     */
    public CommunicationBuilder addTimeSlot(@NotNull Temporal from,
                                            @NotNull Temporal to,
                                            long taskId,
                                            long roomId) {
        long t1 = temporalToSeconds(from, false);
        long t2 = temporalToSeconds(to, false);
        typeOfCommunication = CommunicationType.ADD_TIME_SLOT;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("start", t1);
        requestData.put("end", t2);
        requestData.put("task", taskId);
        requestData.put("room", roomId);
        return this;
    }

    /**
     * Récupère la liste des créneaux
     *
     * @param from From
     * @param to   To
     * @return Builder non terminé avec URL
     */
    private CommunicationBuilder getTimeSlotList(@NotNull Temporal from,
                                                 @NotNull Temporal to) {
        long t1 = temporalToSeconds(from, false);
        long t2 = temporalToSeconds(to, false);
        typeOfCommunication = CommunicationType.GET_TIME_SLOT_LIST;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("from", t1);
        requestData.put("to", t2);
        return this;
    }

    /**
     * Récupère la liste des créneaux entre deux dates <br>
     * Si un créneau commence avant t1 mais se termine entre t1 et t2 il sera pris en compte <br>
     * Fonctionne avec des dates pures (LocalDate) et des dates + temps (LocalDateTime) <br>
     * <br>
     * Exemple d'utilisation : <code>
     * LocalDateTime from = LocalDateTime.of(2020, 1, 1, 15, 30); // Date et heure, 1er janvier 2020 à 15h30 <br>
     * LocalDate to = LocalDate.of(2020, 12, 31); // Date seulement, 31 décembre 2020 à minuit <br>
     * <br>
     * Communication c = Communication.builder() <br>
     * .getUserTimeSlotList(from, to) <br>
     * .startNow() <br>
     * .sleepUntilFinished() <br>
     * .build(); </code>
     *
     * @param from Date de début
     * @param to   Date de fin
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getUserTimeSlotList(@NotNull Temporal from, @NotNull Temporal to) {
        return getTimeSlotList(from, to);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Envoie un message
     *
     * @param content Contenu
     * @param dst     Type de destination
     * @param id      Id de la destination
     * @return Builder non terminé avec URL
     * @see MessageResource
     */
    public CommunicationBuilder sendMessage(@NotNull String content, @NotNull MessageResource dst, long id) {
        typeOfCommunication = CommunicationType.SEND_MESSAGE;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("content", content);
        requestData.put("destination", dst.toString());
        requestData.put("id", id);
        return this;
    }

    /**
     * Récupère la liste des messages
     *
     * @param page   Numéro de page
     * @param id     Id d'à qui récupérer les messages
     * @param origin Origine des messages
     * @return Builder non terminé avec URL
     * @see MessageResource
     */
    public CommunicationBuilder getMessageList(int page, long id, @NotNull MessageResource origin) {
        typeOfCommunication = CommunicationType.LIST_MESSAGES;
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("origin", origin.toString());
        requestData.put("id", id);
        requestData.put("page", page);
        return this;
    }

    /**
     * Récupère la liste des messages de l'utilisateur
     *
     * @param page Numéro de page
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getUserMessageList(int page) {
        return getMessageList(page, User.getUser().getResourceId(), MessageResource.MESSAGE_RESOURCE_HUMAN);
    }

    /**
     * Récupère la liste des messages pour un projet
     *
     * @param page      Numéro de page
     * @param idProject Id du projet
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getProjectMessageList(int page, long idProject) {
        return getMessageList(page, idProject, MessageResource.MESSAGE_RESOURCE_PROJECT);
    }
}
