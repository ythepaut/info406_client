package fr.groupe4.clientprojet.model.project.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

/**
 * Status d'un projet
 */
public enum ProjectStatus {
    PENDING("PENDING"),
    FINISHED("FINISHED"),
    ONGOING("ONGOING"),
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
    ProjectStatus(@NotNull String msg) {
        this.msg = msg.toUpperCase();
    }

    /**
     * Transforme une String en son status associé
     *
     * @param msg Message
     * @return Status associé
     * @throws IllegalArgumentException S'il n'y a pas d'enum associée
     */
    @NotNull
    public static ProjectStatus fromString(@NotNull String msg) throws IllegalArgumentException {
        ProjectStatus[] vars = ProjectStatus.values();

        ProjectStatus result = null;

        for (ProjectStatus var : vars) {
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
