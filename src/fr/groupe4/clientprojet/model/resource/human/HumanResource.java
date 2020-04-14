package fr.groupe4.clientprojet.model.resource.human;

import fr.groupe4.clientprojet.model.resource.Resource;
import fr.groupe4.clientprojet.model.resource.ResourceType;
import fr.groupe4.clientprojet.model.resource.human.enums.HumanRole;
import org.jetbrains.annotations.NotNull;

/**
 * Ressource humaine
 */
public class HumanResource extends Resource {
    /**
     * Prénom
     */
    @NotNull
    private String firstname;

    /**
     * Nom
     */
    @NotNull
    private String lastname;

    /**
     * Job
     */
    @NotNull
    private String job;

    /**
     * Rôle
     */
    @NotNull
    private HumanRole role;

    /**
     * Description
     */
    @NotNull
    private String description;

    /**
     * Constructeur
     *
     * @param resourceId Id
     * @param firstname Prénom
     * @param lastname Nom
     * @param job Job
     * @param role Rôle
     * @param description Description
     */
    public HumanResource(long resourceId, @NotNull String firstname, @NotNull String lastname, @NotNull String job, @NotNull String role, @NotNull String description) {
        super(ResourceType.HUMAN_RESOURCE, resourceId);

        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.role = HumanRole.fromString(role);
        this.description = description;
    }

    /**
     * Permet de copier une ressource humaine
     */
    public HumanResource(HumanResource toCopy) {
        this(toCopy.getResourceId(), toCopy.firstname, toCopy.lastname, toCopy.job, toCopy.role.toString(), toCopy.description);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getJob() {
        return job;
    }

    public String getDescription() {
        return description;
    }

    public HumanRole getRole() {
        return role;
    }

    public String toString() {
        return firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase() + " "
                + lastname.toUpperCase() + ", " + role + ", " + job;
    }
}
