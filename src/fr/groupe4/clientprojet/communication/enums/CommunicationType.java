package fr.groupe4.clientprojet.communication.enums;

import org.jetbrains.annotations.NotNull;

/**
 * Types de communication
 */
public enum CommunicationType {
    DEFAULT,

    LOGIN("/auth/connect", false),
    UPDATE_CONNECTION("/auth/renew", false),
    VERIFY_CONNECTION, // TODO ? -> GET_USER_INFOS

    CREATE_PROJECT("/project/create"),
    GET_PROJECT("/project/get"),
    LIST_PROJECTS("/project/list"),
    ADD_RESOURCE_TO_PROJECT("/project/alloc"),
    REMOVE_RESOURCE_FROM_PROJECT("/project/unalloc"),

    CREATE_HUMAN_RESOURCE, // TODO
    GET_USER_INFOS("/auth/verify"),
    GET_HUMAN_RESOURCE("/resource/h/get"),
    LIST_HUMAN_RESOURCE("/resource/h/list"),

    CREATE_MATERIAL_RESOURCE, // TODO
    GET_MATERIAL_RESOURCE, // TODO
    LIST_MATERIAL_RESOURCE, // TODO

    CREATE_TASK("/task/create"), // TODO
    GET_TASK, // TODO
    GET_TASK_LIST("/task/list"),

    ADD_TIME_SLOT("/timeslot/create"),
    GET_TIME_SLOT_LIST("/timeslot/list"),
    GET_TIME_SLOT, // TODO
    REMOVE_TIME_SLOT, // TODO

    CREATE_ROOM, // TODO
    GET_ROOM, // TODO
    LIST_ROOMS, // TODO

    SEND_MESSAGE("/message/create"),
    LIST_MESSAGES("/message/list");

    /**
     * Doit vérifier la connexion ou non
     */
    private final boolean checkConnection;

    /**
     * URL associé
     */
    @NotNull
    private final String url;

    /**
     * Constructeur
     */
    CommunicationType() {
        this("");
    }

    /**
     * Constructeur
     *
     * @param url URL
     */
    CommunicationType(@NotNull String url) {
        this(url, true);
    }

    /**
     * Constructeur
     *
     * @param url             URL
     * @param checkConnection Vérifie la connexion
     */
    CommunicationType(@NotNull String url, boolean checkConnection) {
        this.url = url;
        this.checkConnection = checkConnection;
    }

    /**
     * Récupère l'URL
     *
     * @return URL
     */
    @NotNull
    public String getUrl() {
        return url;
    }

    /**
     * Doit vérifier la connexion ?
     *
     * @return Vérif ?
     */
    public boolean checkConnection() {
        return checkConnection;
    }
}
