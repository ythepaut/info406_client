package fr.groupe4.clientprojet.message.enums;

public enum MessageResource {
    ORIGIN_HUMANRESOURCE("HUMANRESOURCE"),
    ORIGIN_PROJECT("PROJECT"),
    ORIGIN_HUMANRESOURCE_ALLOCATION("HUMANRESOURCE_ALLOCATION"),
    ORIGIN_MATERIALRESOURCE_ALLOCATION("MATERIALRESOURCE_ALLOCATION");

    private String msg;

    MessageResource(String msg) {
        this.msg = msg.toUpperCase();
    }

    /**
     * Transforme une String en son enum associée
     *
     * @param msg Message
     *
     * @return Enum associé
     */
    public static MessageResource fromString(String msg) {
        MessageResource[] vars = MessageResource.values();

        MessageResource result = null;

        for (MessageResource var : vars) {
            if (var.msg.equalsIgnoreCase(msg)) {
                result = var;
            }
        }

        return result;
    }

    /**
     * Transforme une enum en String
     *
     * @return Message
     */
    @Override
    public String toString() {
        return msg;
    }
}
