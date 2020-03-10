package fr.groupe4.clientprojet.communication.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

/**
 * Status de la communication
 */
public enum CommunicationStatus {
    STATUS_DEFAULT("default"),
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

    @NotNull
    public static CommunicationStatus fromString(String msg) throws IllegalArgumentException {
        CommunicationStatus[] vars = CommunicationStatus.values();

        CommunicationStatus result = null;

        for (CommunicationStatus var : vars) {
            if (var.msg.equalsIgnoreCase(msg)) {
                result = var;
            }
        }

        if (result == null) {
            String errorMsg = "Pas d'enum provenant de la chaine '" + msg + "'";
            Logger.error(errorMsg, LoggerOption.LOG_FILE_ONLY);
            throw new IllegalArgumentException(errorMsg);
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
