package fr.groupe4.clientprojet.exitdialog.enums;

public enum ExitChoice {
    EXIT("exit"),
    CANCEL("cancel");

    private String name;

    ExitChoice(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

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
