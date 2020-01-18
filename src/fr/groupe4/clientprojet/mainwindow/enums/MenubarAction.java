package fr.groupe4.clientprojet.mainwindow;

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
            case "setting":
                result = MenubarAction.SETTING;
            case "exit":
                result = MenubarAction.EXIT;
            case "addTask":
                result = MenubarAction.ADDTASK;
            case "deleteTask":
                result = MenubarAction.DELETETASK;
        }

        return result;
    }
}
