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
    private String msg;

    /**
     * Constructeur
     *
     * @param msg Message
     */
    CommunicationStatus(String msg) {
        this.msg = msg.toLowerCase();
    }

    public static CommunicationStatus fromString(String msg) {
        CommunicationStatus[] vars = CommunicationStatus.values();

        CommunicationStatus result = null;

        for (CommunicationStatus var : vars) {
            if (var.msg.equalsIgnoreCase(msg)) {
                result = var;
            }
        }

        return result;
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
