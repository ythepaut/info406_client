package fr.groupe4.clientprojet.communication.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

/**
 * Propriétés de la communication, utilisées pour les event
 */
public enum CommunicationPropertyName {
    COMMUNICATION_LOADING_FINISHED("loadingFinished"),
    COMMUNICATION_NEWS_CHANGED("newsChanged");

    /**
     * Nom de l'event
     */
    @NotNull
    private final String name;

    /**
     * Constructeur
     *
     * @param name Nom de la propriété
     */
    CommunicationPropertyName(@NotNull String name) {
        this.name = name;
    }

    /**
     * Vers chaine
     *
     * @return Nom
     */
    @NotNull
    @Override
    public String toString() {
        return name;
    }

    /**
     * String vers enum
     *
     * @param name Nom
     * @return Enum
     * @throws IllegalArgumentException Si enum non trouvée
     */
    @NotNull
    public static CommunicationPropertyName fromString(@NotNull String name) throws IllegalArgumentException {
        CommunicationPropertyName[] vars = CommunicationPropertyName.values();

        CommunicationPropertyName result = null;

        for (CommunicationPropertyName var : vars) {
            if (var.name.equals(name)) {
                result = var;
            } else if (var.name.equalsIgnoreCase(name)) {
                Logger.warning("Même nom mais case différente :", name);
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
