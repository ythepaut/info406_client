package fr.groupe4.clientprojet.connectiondialog.enums;

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
        ConnectionChoice res = null;

        switch (name) {
            case "ok":
                res = ConnectionChoice.OK;
                break;

            case "cancel":
                res = ConnectionChoice.CANCEL;
                break;
        }

        return res;
    }
}
