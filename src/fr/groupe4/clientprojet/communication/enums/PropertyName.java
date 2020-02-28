package fr.groupe4.clientprojet.communication.enums;

public enum PropertyName {
    LOADDIALOG("loadingFinished");

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

            default:
        }

        return res;
    }
}
