package fr.groupe4.clientprojet.communication.enums;

public enum PropertyName {
    LOADDIALOG("loadingFinished"),
    NEWS("newsChanged");

    private final String name;

    PropertyName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static PropertyName getEnum(String name) {
        PropertyName res = null;

        switch (name) {
            case "loadingFinished":
                res = LOADDIALOG;
                break;

            case "newsChanged":
                res = NEWS;
                break;

            default:
        }

        return res;
    }
}
