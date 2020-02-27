package fr.groupe4.clientprojet.project.enums;

public enum ProjectStatus {
    PENDING("PENDING"),
    FINISHED("FINISHED"),
    ONGOING("ONGOING"),
    CANCELED("CANCELED");

    private String msg;

    ProjectStatus(String msg) {
        this.msg = msg.toUpperCase();
    }

    /**
     * Transforme une String en son status associé
     *
     * @param msg Message
     *
     * @return Status associé
     */
    public static ProjectStatus fromString(String msg) {
        ProjectStatus[] vars = ProjectStatus.values();

        ProjectStatus result = null;

        for (ProjectStatus var : vars) {
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
