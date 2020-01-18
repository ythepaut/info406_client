package fr.groupe4.clientprojet.mainwindow.enums;

public enum MenubarAction {
    CONNECTION("connection"),
    SETTING("setting"),
    EXIT("exit"),
    ADDTASK("addTask"),
    DELETETASK("deleteTask");

    private String name;

    MenubarAction(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static MenubarAction getEnum(String name) {

        MenubarAction result = null;

        switch (name) {
            case "connection":
                result = MenubarAction.CONNECTION;
                break;
            case "setting":
                result = MenubarAction.SETTING;
                break;
            case "exit":
                result = MenubarAction.EXIT;
                break;
            case "addTask":
                result = MenubarAction.ADDTASK;
                break;
            case "deleteTask":
                result = MenubarAction.DELETETASK;
                break;
        }

        return result;
    }
}
