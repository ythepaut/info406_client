package fr.groupe4.clientprojet.model.resource;

public enum ResourceType {
    HUMAN_RESOURCE("HUMAN_RESOURCE", "HUMAN"),
    MATERIAL_RESOURCE("MATERIAL_RESOURCE", "MATERIAL");

    String name;
    String nameForAPI;

    ResourceType(String name, String nameForAPI) {
        this.name = name;
        this.nameForAPI = nameForAPI;
    }

    public String toString() {
        return name;
    }

    public String getNameForAPI() {
        return nameForAPI;
    }
}
