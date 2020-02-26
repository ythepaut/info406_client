package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.communication.enums.CommunicationType;
import fr.groupe4.clientprojet.project.enums.ProjectStatus;

import java.util.HashMap;

import static fr.groupe4.clientprojet.communication.enums.CommunicationType.*;

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
