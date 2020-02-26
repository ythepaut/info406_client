package fr.groupe4.clientprojet.communication.enums;

/**
 * Status de la communication
 */
public enum CommunicationStatus {
    STATUS_SUCCESS("success"),
    STATUS_ERROR("error");

    /**
     * Message associé
     */
    String msg;

    /**
     * Constructeur
     *
     * @param msg Message
     */
    CommunicationStatus(String msg) {
        this.msg = msg.toLowerCase();
    }

    /**
     * Transforme une String en son status associé
     *
     * @param msg Message
     *
     * @return Status associé
     */
    public static CommunicationStatus fromString(String msg) {
        CommunicationStatus[] statuses = CommunicationStatus.values();

        CommunicationStatus statusResult = null;

        for (CommunicationStatus status : statuses) {
            if (status.msg.equalsIgnoreCase(msg)) {
                statusResult = status;
            }
        }

        return statusResult;
    }

    /**
     * Transforme un status en String
     *
     * @return Message
     */
    @Override
    public String toString() {
        return msg;
    }
}
