package fr.groupe4.clientprojet.display.view.messagepanel.enums;

public enum MessageButton {
    SEND("send"),
    REFRESH("refresh");

    private String name;

    MessageButton(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static MessageButton getEnum(String name) {
        MessageButton res = null;

        switch (name) {
            case "send":
                res = SEND;
                break;

            case "refresh":
                res = REFRESH;
                break;

            default:
        }


        return res;
    }
}
