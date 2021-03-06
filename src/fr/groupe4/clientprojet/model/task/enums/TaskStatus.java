package fr.groupe4.clientprojet.model.task.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

/**
 * Status des tâches
 */
public enum TaskStatus {
    PENDING("PENDING"),
    FINISHED("FINISHED"),
    ONGOING("ONGOING"),
    REVIEWING("REVIEWING"),
    CANCELED("CANCELED");

    /**
     * Message
     */
    @NotNull
    private final String msg;

    /**
     * Constructeur
     *
     * @param msg Message
     */
    TaskStatus(@NotNull String msg) {
        this.msg = msg;
    }

    /**
     * Transforme une String en son status associé
     *
     * @param msg Message
     * @return Status associé
     * @throws IllegalArgumentException S'il n'y a pas d'enum associée
     */
    @NotNull
    public static TaskStatus fromString(@NotNull String msg) throws IllegalArgumentException {
        TaskStatus[] vars = TaskStatus.values();

        TaskStatus result = null;

        for (TaskStatus var : vars) {
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
     * @return String
     */
    @NotNull
    @Override
    public String toString() {
        return msg;
    }
}
