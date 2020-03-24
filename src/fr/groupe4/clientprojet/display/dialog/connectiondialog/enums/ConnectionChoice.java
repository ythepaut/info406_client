package fr.groupe4.clientprojet.display.dialog.connectiondialog.enums;

/**
 * Les choix pour les boutons de connexion
 */
public enum ConnectionChoice {
    OK("ok"),
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
    ConnectionChoice(String name) {
        this.name = name;
    }

    /**
     * Renvoie le nom de l'enum
     *
     * @return : le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie l'enum correspondante au nom
     *
     * @param name : le nom
     * @return : l'enum
     */
    public static ConnectionChoice getEnum(String name) {

        ConnectionChoice result = null;

        switch (name) {
            case "ok":
                result = OK;
                break;
            case "cancel":
                result = CANCEL;
                break;

            default:
        }

        return result;
    }
}
