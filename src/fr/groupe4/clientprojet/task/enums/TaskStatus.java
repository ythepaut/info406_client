package fr.groupe4.clientprojet.task.enums;

public enum TaskStatus {
    PENDING("PENDING"),
    FINISHED("FINISHED"),
    ONGOING("ONGOING"),
    REVIEWING("REVIEWING"),
    CANCELED("CANCELED");

    private String msg;

    TaskStatus(String msg) {
        this.msg = msg.toUpperCase();
    }

    /**
     * Transforme une String en son status associé
     *
     * @param msg Message
     *
     * @return Status associé
     */
    public static TaskStatus fromString(String msg) {
        TaskStatus[] vars = TaskStatus.values();

        TaskStatus result = null;

        for (TaskStatus var : vars) {
            if (var.msg.equalsIgnoreCase(msg)) {
                result = var;
            }
        }

        return result;
    }

    /**
     * Transforme un status en String
     *
     * @return Message
     */
    @Override
    public String toString() {
        return msg;
    }
}
