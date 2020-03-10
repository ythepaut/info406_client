package fr.groupe4.clientprojet.logger.enums;

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

    private String msg;
    private LoggerColor textColor;

    LoggerType(String msg, LoggerColor textColor) {
        this.msg = msg;
        this.textColor = textColor;
    }

    @Override
    public String toString() {
        return msg.toUpperCase();
    }

    public LoggerColor getTextColor() {
        return textColor;
    }
}
