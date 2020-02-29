package fr.groupe4.clientprojet.communication.enums;

public enum CommunicationPropertyName {
    COMMUNICATION_LOADING_FINISHED("loadingFinished"),
    COMMUNICATION_NEWS_CHANGED("newsChanged");

    private final String name;

    CommunicationPropertyName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static CommunicationPropertyName fromString(String name) {
        CommunicationPropertyName[] vars = CommunicationPropertyName.values();

        CommunicationPropertyName result = null;

        for (CommunicationPropertyName var : vars) {
            if (var.name.equalsIgnoreCase(name)) {
                result = var;
            }
        }

        return result;
    }

    public static CommunicationPropertyName getEnum(String name) {
        return fromString(name);
    }
}
