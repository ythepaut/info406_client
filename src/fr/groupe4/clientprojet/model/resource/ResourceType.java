package fr.groupe4.clientprojet.model.resource;

public enum ResourceType {
    HUMAN_RESOURCE("HUMAN_RESOURCE");

    String name;

    ResourceType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
