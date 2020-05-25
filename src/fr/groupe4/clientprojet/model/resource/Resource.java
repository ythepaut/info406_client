package fr.groupe4.clientprojet.model.resource;

import org.jetbrains.annotations.NotNull;

/**
 * Ressource
 */
public class Resource {
    /**
     * Type de la ressource
     */
    @NotNull
    private final ResourceType resourceType;

    /**
     * Id de la ressource
     */
    private final long resourceId;

    /**
     * Constructeur
     *
     * @param type Type de la ressource
     * @param id   Id de la ressource
     */
    public Resource(@NotNull ResourceType type, long id) {
        resourceType = type;
        resourceId = id;
    }

    /**
     * Récupère l'id de la ressource
     *
     * @return Id
     */
    public long getResourceId() {
        return resourceId;
    }

    /**
     * Récupère le type de la ressource
     *
     * @return Type de ressource
     */
    @NotNull
    public ResourceType getResourceType() {
        return resourceType;
    }
}
