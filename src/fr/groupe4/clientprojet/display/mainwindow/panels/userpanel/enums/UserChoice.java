package fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.enums;

/**
 * L'enumeration pour la modification de l'utilisateur
 */
public enum UserChoice {
    PASSWORD("password"),
    MAIL("mail"),
    SETTINGS("settings");

    /**
     * Le nom du choix
     */
    private String name;

    /**
     * Le constructeur
     *
     * @param name : le nom du choix
     */
    UserChoice(String name) {
        this.name = name;
    }

    /**
     * Renvoie le nom du choix
     *
     * @return : le nom du choix
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie l'enum correspondant au nom
     *
     * @param name : le nom
     * @return : l'enum
     */
    public static UserChoice getEnum(String name) {
        UserChoice res = null;

        switch (name) {
            case "password":
                res = PASSWORD;
                break;

            case "mail":
                res = MAIL;
                break;

            case "settings":
                res = SETTINGS;
                break;

            default:
        }

        return res;
    }
}
