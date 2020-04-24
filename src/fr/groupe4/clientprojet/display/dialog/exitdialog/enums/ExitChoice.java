package fr.groupe4.clientprojet.display.dialog.exitdialog.enums;

import fr.groupe4.clientprojet.logger.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * L'enumération pour les boutons du dialog
 */
public enum ExitChoice {
    EXIT("exit"),
    CANCEL("cancel");

    /**
     * Le nom du choix
     */
    @NotNull
    private final String name;

    /**
     * Le constructeur
     *
     * @param name Nom du choix
     */
    ExitChoice(@NotNull String name) {
        this.name = name;
    }

    /**
     * Renvoie le nom du choix
     *
     * @return Nom du choix
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
     * @throws IllegalArgumentException S'il n'y a pas d'enum associée
     */
    @NotNull
    public static ExitChoice getEnum(@NotNull String name) throws IllegalArgumentException {
        ExitChoice result;

        switch (name) {
            case "exit":
                result = ExitChoice.EXIT;
                break;
            case "cancel":
                result = ExitChoice.CANCEL;
                break;

            default:
                Logger.error("Pas d'enum", name);
                throw new IllegalArgumentException("Pas d'enum");
        }

        return result;
    }
}
