package fr.groupe4.clientprojet.model.resource;

import org.jetbrains.annotations.NotNull;

/**
 * Type de ressource
 */
public enum ResourceType {
    HUMAN_RESOURCE("HUMAN_RESOURCE", "HUMAN"),
    MATERIAL_RESOURCE("MATERIAL_RESOURCE", "MATERIAL");

    /**
     * Nom
     */
    @NotNull
    private final String name;

    /**
     * Nom pour l'API
     */
    @NotNull
    private final String nameForAPI;

    /**
     * Constructeur
     *
     * @param name       Nom
     * @param nameForAPI Nom pour l'API
     */
    ResourceType(@NotNull String name, @NotNull String nameForAPI) {
        this.name = name;
        this.nameForAPI = nameForAPI;
    }

    /**
     * Vers String
     *
     * @return String
     */
    @NotNull
    @Override
    public String toString() {
        return name;
    }

    /**
     * Récupère le nom pour l'API
     *
     * @return Nom API
     */
    @NotNull
    public String getNameForAPI() {
        return nameForAPI;
    }
}
