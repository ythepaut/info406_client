package fr.groupe4.clientprojet.communication.enums;

/**
 * Types de communication
 */
public enum CommunicationType {
    DEFAULT,

    LOGIN(false),
    UPDATE_CONNECTION(false),
    VERIFY_CONNECTION, // TODO

    CREATE_PROJECT,
    GET_PROJECT,
    LIST_PROJECTS,

    CREATE_HUMAN_RESOURCE, // TODO
    GET_USER_INFOS,
    GET_HUMAN_RESOURCE,
    LIST_HUMAN_RESOURCE,

    CREATE_MATERIAL_RESOURCE, // TODO
    GET_MATERIAL_RESOURCE, // TODO
    LIST_MATERIAL_RESOURCE, // TODO

    CREATE_TASK, // TODO
    GET_TASK, // TODO
    GET_TASK_LIST,

    ADD_TIME_SLOT,
    GET_TIME_SLOT_LIST,
    GET_TIME_SLOT, // TODO
    REMOVE_TIME_SLOT, // TODO

    CREATE_ROOM, // TODO
    GET_ROOM, // TODO
    LIST_ROOMS, // TODO

    SEND_MESSAGE,
    LIST_MESSAGES;

    private boolean checkConnection;

    CommunicationType() {
        this(true);
    }

    CommunicationType(boolean checkConnection) {
        this.checkConnection = checkConnection;
    }

    public boolean checkConnection() {
        return checkConnection;
    }
}
