package fr.groupe4.clientprojet.logger.enums;

import org.jetbrains.annotations.NotNull;

import static fr.groupe4.clientprojet.logger.enums.LoggerColor.*;

/**
 * Types de log
 */
public enum LoggerType {
    INFO("INFO", ANSI_BLUE),
    STATS("STATS", ANSI_CYAN),
    SUCCESS("SUCCESS", ANSI_GREEN),
    ERROR("ERROR", ANSI_RED),
    WARNING("WARNING", ANSI_YELLOW),
    DEBUG("DEBUG", ANSI_PURPLE);

    /**
     * Description du type de log
     */
    @NotNull
    private final String msg;

    /**
     * Couleur du log
     */
    @NotNull
    private final LoggerColor textColor;

    /**
     * Constructeur
     *
     * @param msg Message, nom
     * @param textColor Couleur
     */
    LoggerType(@NotNull String msg, @NotNull LoggerColor textColor) {
        this.msg = msg;
        this.textColor = textColor;
    }

    /**
     * Vers String
     *
     * @return String
     */
    @NotNull
    @Override
    public String toString() {
        return msg.toUpperCase();
    }

    /**
     * Récupère la couleur associée
     *
     * @return Couleur enum
     */
    @NotNull
    public LoggerColor getTextColor() {
        return textColor;
    }
}
