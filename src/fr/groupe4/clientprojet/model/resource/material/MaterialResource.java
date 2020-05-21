package fr.groupe4.clientprojet.model.resource.material;

import fr.groupe4.clientprojet.model.resource.Resource;
import fr.groupe4.clientprojet.model.resource.ResourceType;
import org.jetbrains.annotations.NotNull;

/**
 * Ressource matérielle
 */
public class MaterialResource extends Resource {
    /**
     * Nom de la ressource
     */
    @NotNull
    private final String name;

    /**
     * Description de la ressource
     */
    @NotNull
    private final String description;

    /**
     * Constructeur
     *
     * @param id   Id de la ressource
     * @param name Nom de la ressource
     */
    public MaterialResource(long id, @NotNull String name, @NotNull String description) {
        super(ResourceType.MATERIAL_RESOURCE, id);

        this.name = name;
        this.description = description;
    }

    /**
     * Constructeur copiant une ressource matérielle
     *
     * @param materialResource Ressource matérielle
     */
    public MaterialResource(@NotNull MaterialResource materialResource) {
        this(
                materialResource.getResourceId(),
                materialResource.getName(),
                materialResource.getDescription());
    }

    /**
     * Récupération du nom
     *
     * @return Nom
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Récupération de la description
     *
     * @return Description
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    /**
     * Vers String
     *
     * @return String
     */
    @NotNull
    @Override
    public String toString() {
        return getName() + ":" + getDescription();
    }
}
