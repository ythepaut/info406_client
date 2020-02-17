package fr.groupe4.clientprojet.exitdialog.enums;

/**
 * L'enumération pour les boutons du dialog
 */
public enum ExitChoice {
    EXIT("exit"),
    CANCEL("cancel");

    /**
     * Le nom du choix
     */
    private String name;

    /**
     * Le constructeur
     *
     * @param name : le nom du choix
     */
    ExitChoice(String name) {
        this.name = name;
    }

    /**
     * Renvoie le nom du choix
     *
     * @return : le nom du choix
     */
    public String getName() {
        return this.name;
    }

    /**
     * Renvoie l'enum correspondante au nom
     *
     * @param name : le nom
     * @return : l'enum
     */
    public static ExitChoice getEnum(String name) {

        ExitChoice result = null;

        switch (name) {
            case "exit":
                result = ExitChoice.EXIT;
                break;
            case "cancel":
                result = ExitChoice.CANCEL;
                break;

            default:
        }

        return result;
    }
}
