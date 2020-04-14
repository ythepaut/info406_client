package fr.groupe4.clientprojet.model.resource;

public class Resource {
    /**
     * Type de la ressource
     */
    private final ResourceType resourceType;

    /**
     * Id de la ressource
     */
    private long resourceId;

    public Resource(ResourceType type, long id) {
        resourceType = type;
        resourceId = id;
    }

    public long getResourceId() {
        return resourceId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
