package fr.groupe4.clientprojet.communication.enums;

import fr.groupe4.clientprojet.communication.Communication;

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

    CREATE_HUMAN_RESOURCE, // TODO
    GET_USER_INFOS("/auth/verify"),
    GET_HUMAN_RESOURCE("/resource/h/get"),
    LIST_HUMAN_RESOURCE("/resource/h/list"), // TODO

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

    private boolean checkConnection;
    private String url;

    CommunicationType() {
        this("");
    }

    CommunicationType(String url) {
        this(url, true);
    }

    CommunicationType(String url, boolean checkConnection) {
        this.url = url;
        this.checkConnection = checkConnection;
    }

    public String getUrl() {
        return url;
    }

    public boolean checkConnection() {
        return checkConnection;
    }
}
