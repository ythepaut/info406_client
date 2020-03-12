package fr.groupe4.clientprojet.communication.enums;

/**
 * Types de communication
 */
public enum CommunicationType {
    DEFAULT,
    LOGIN(false),
    UPDATE_CONNECTION(false),
    LIST_PROJECTS,
    GET_USER_INFOS,
    GET_HUMAN_RESOURCE,
    LIST_USER_MESSAGES,
    GET_TIME_SLOT_LIST,
    ADD_TIME_SLOT,
    GET_TASK_LIST,
    CREATE_PROJECT;

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
