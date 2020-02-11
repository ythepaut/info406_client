package fr.groupe4.clientprojet.connectiondialog.enums;

import fr.groupe4.clientprojet.exitdialog.enums.ExitChoice;

public enum ConnectionChoice {
    OK("ok"),
    CANCEL("cancel");

    private String name;

    ConnectionChoice(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

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
