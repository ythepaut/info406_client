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
    @NotNull
    private final String msg;

    /**
     * Constructeur
     *
     * @param msg Message
     */
    CommunicationStatus(@NotNull String msg) {
        this.msg = msg;
    }

    /**
     * Chaine vers enum
     *
     * @param msg Chaine
     * @return Enum
     * @throws IllegalArgumentException Enum non trouvée
     */
    @NotNull
    public static CommunicationStatus fromString(@NotNull String msg) throws IllegalArgumentException {
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
    @NotNull
    @Override
    public String toString() {
        return msg;
    }
}
