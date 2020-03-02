package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.communication.enums.CommunicationType;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.project.enums.ProjectStatus;
import fr.groupe4.clientprojet.resource.human.User;
import fr.groupe4.clientprojet.room.Room;
import fr.groupe4.clientprojet.task.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.HashMap;

import static fr.groupe4.clientprojet.communication.enums.CommunicationType.*;
import static fr.groupe4.clientprojet.message.enums.MessageResource.*;

/**
 * Builder de la communication
 */
public final class CommunicationBuilder {
    /**
     * Type de communication
     */
    protected CommunicationType typeOfCommunication;

    /**
     * URL à envoyer à l'API
     */
    protected String url = null;

    /**
     * Se lance tout de suite après le constructeur ou nécessite un comm.start()
     */
    protected boolean startNow;

    protected boolean keepAlive;

    /**
     * Attend que la requête soit terminée et bloque le thread
     * Cette variable ne sert que si startNow est à true
     */
    protected boolean sleepUntilFinished;

    /**
     * Data à envoyer en POST pour la requête
     */
    protected HashMap<String, Object> requestData;

    /**
     * Constructeur
     */
    public CommunicationBuilder() {
        startNow = false;
        keepAlive = false;
        sleepUntilFinished = false;
        requestData = new HashMap<>();
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
    protected CommunicationBuilder keepAlive() {
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

    public CommunicationBuilder createProject(String name, String description, long deadline, ProjectStatus status) {
        typeOfCommunication = CREATE_PROJECT;
        url = "project/create";
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("name", name);
        requestData.put("description", description);
        requestData.put("deadline", deadline);
        requestData.put("status", status.toString());
        return this;
    }

    /**
     * Récupère la liste des créneaux entre deux dates
     * Si un créneau commence avant t1 mais se termine entre t1 et t2 il sera pris en compte
     * Fonctionne avec des dates pures (LocalDate) et des dates + temps (LocalDateTime)
     *
     * Exemple d'utilisation :
     *      LocalDateTime from = LocalDateTime.of(2020, 1, 1, 15, 30); // Date et heure, 1er janvier 2020 à 15h30
     *      LocalDate to = LocalDate.of(2020, 12, 31); // Date seulement, 31 décembre 2020
     *
     *      Communication c = Communication.builder()
     *          .getUserTimeSlotList(from, to)
     *          .sleepUntilFinished()
     *          .startNow()
     *          .build();
     *
     * @param from Date de début
     * @param to Date de fin
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getUserTimeSlotList(Temporal from, Temporal to) {
        return getTimeSlotList(from, to, "hresource", User.getUser().getResourceId());
    }

    private CommunicationBuilder getTimeSlotList(Temporal from, Temporal to, String what, long id) {
        long t1 = 0, t2 = 0;

        if (from instanceof LocalDate) t1 = ((LocalDate) from).atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
        else if (from instanceof LocalDateTime) t1 = ((LocalDateTime) from).atZone(ZoneId.systemDefault()).toEpochSecond();
        else Logger.error("From : type incorrect");

        if (to instanceof LocalDate) t2 = ((LocalDate) to).atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
        else if (to instanceof LocalDateTime) t2 = ((LocalDateTime) to).atZone(ZoneId.systemDefault()).toEpochSecond();
        else Logger.error("To : type incorrect");

        typeOfCommunication = GET_TIME_SLOT_LIST;
        url = "timeslot/list";
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("from", t1);
        requestData.put("to", t2);
        requestData.put(what, id);
        return this;
    }

    /**
     * Ajoute un créneau
     * @see #getUserTimeSlotList getUserTimeSlotList pour exemple détaillé d'utilisation de from et de to
     *
     * @param from Date de début
     * @param to Date de fin
     * @param task Tâche
     * @param room Salle
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder addTimeSlot(Temporal from, Temporal to, Task task, Room room) {
        long t1 = 0, t2 = 0;

        if (from instanceof LocalDate) t1 = ((LocalDate) from).atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
        else if (from instanceof LocalDateTime) t1 = ((LocalDateTime) from).atZone(ZoneId.systemDefault()).toEpochSecond();
        else Logger.error("From : type incorrect");

        if (to instanceof LocalDate) t2 = ((LocalDate) to).atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
        else if (to instanceof LocalDateTime) t2 = ((LocalDateTime) to).atZone(ZoneId.systemDefault()).toEpochSecond();
        else Logger.error("To : type incorrect");

        typeOfCommunication = ADD_TIME_SLOT;
        url = "timeslot/create";
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("start", t1);
        requestData.put("end", t2);
        requestData.put("task", task.getId());
        requestData.put("room", room.getId());
        return this;
    }

    public CommunicationBuilder getUserMessageList(int page) {
        typeOfCommunication = LIST_USER_MESSAGES;
        url = "message/list";
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("origin", ORIGIN_HUMANRESOURCE.toString());
        requestData.put("id", User.getUser().getResourceId());
        requestData.put("page", page);
        return this;
    }

    /**
     * Récupère les infos de l'utilisateur
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getUserInfos() {
        typeOfCommunication = GET_USER_INFOS;
        url = "auth/verify";
        requestData.put("token", Communication.getRequestToken(this));
        return this;
    }

    /**
     * Récupère une ressource humaine
     *
     * @param id Id de la ressource humaine
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getHumanRessource(long id) {
        typeOfCommunication = GET_HUMAN_RESOURCE;
        url = "resource/h/get";
        requestData.put("token", Communication.getRequestToken(this));
        requestData.put("id", id);
        return this;
    }

    /**
     * Vérifie la connexion
     *
     * @return Builder non terminé avec URL
     */
    protected CommunicationBuilder updateConnection() {
        typeOfCommunication = UPDATE_CONNECTION;
        url = "auth/renew";
        requestData.put("token", Communication.getRenewToken(this));
        return this;
    }

    /**
     * Récupère la liste des projets
     *
     * @return Builder non terminé avec URL
     */
    public CommunicationBuilder getProjectList() {
        typeOfCommunication = LIST_PROJECTS;
        url = "project/list";
        requestData.put("token", Communication.getRequestToken(this));
        return this;
    }
}
