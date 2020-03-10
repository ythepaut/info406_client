package fr.groupe4.clientprojet.communication.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum APICode {
    NOT_FINISHED("API_CALL_NOT_FINISHED"),
    ERROR("ERROR"),
    SUCCESS("SUCCESS");

    /**
     * Message associé
     */
    private String msg;

    /**
     * Constructeur
     *
     * @param msg Message
     */
    APICode(String msg) {
        this.msg = msg.toUpperCase();
    }

    @NotNull
    public static APICode fromString(@NotNull String msg) throws IllegalArgumentException {
        APICode[] vars = values();
        APICode result = null;

        for (APICode var : vars) {
            if (msg.toUpperCase().contains(var.msg.toUpperCase())) {
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
