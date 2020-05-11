package fr.groupe4.clientprojet.display.dialog.connectiondialog.enums;

import fr.groupe4.clientprojet.logger.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Choix pour les boutons de connexion
 */
public enum ConnectionChoice {
    OK("ok"),
    CANCEL("cancel");

    /**
     * Nom du choix
     */
    @NotNull
    private final String name;

    /**
     * Le constructeur
     *
     * @param name : le nom du choix
     */
    ConnectionChoice(@NotNull String name) {
        this.name = name;
    }

    /**
     * Renvoie le nom de l'enum
     *
     * @return Nom
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Renvoie l'enum correspondante au nom
     *
     * @param name Nom
     *
     * @return Enum
     *
     * @throws IllegalArgumentException Pas d'enum associée
     */
    @NotNull
    public static ConnectionChoice getEnum(@NotNull String name) throws IllegalArgumentException {
        ConnectionChoice result;

        switch (name) {
            case "ok":
                result = OK;
                break;

            case "cancel":
                result = CANCEL;
                break;

            default:
                Logger.error("Pas d'enum", name);
                throw new IllegalArgumentException("Pas d'enum");
        }

        return result;
    }
}
