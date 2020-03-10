package fr.groupe4.clientprojet.communication.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

public enum CommunicationPropertyName {
    COMMUNICATION_LOADING_FINISHED("loadingFinished"),
    COMMUNICATION_NEWS_CHANGED("newsChanged");

    private final String name;

    CommunicationPropertyName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @NotNull
    public static CommunicationPropertyName fromString(String name) throws IllegalArgumentException {
        CommunicationPropertyName[] vars = CommunicationPropertyName.values();

        CommunicationPropertyName result = null;

        for (CommunicationPropertyName var : vars) {
            if (var.name.equalsIgnoreCase(name)) {
                result = var;
            }
        }

        if (result == null) {
            String errorMsg = "Pas d'enum provenant de la chaine '" + name + "'";
            Logger.error(errorMsg, LoggerOption.LOG_FILE_ONLY);
            throw new IllegalArgumentException(errorMsg);
        }

        return result;
    }
}
