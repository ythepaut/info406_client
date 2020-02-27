package fr.groupe4.clientprojet.logger.enums;

public enum LoggerType {
    INFO("INFO"),
    STATS("STATS"),
    SUCCESS("SUCCESS"),
    ERROR("ERROR"),
    WARNING("WARNING"),
    DEBUG("DEBUG");

    private String msg;

    LoggerType(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg.toUpperCase();
    }
}
