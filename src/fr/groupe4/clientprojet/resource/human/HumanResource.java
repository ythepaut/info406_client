package fr.groupe4.clientprojet.resource.human;

import fr.groupe4.clientprojet.resource.human.enums.HumanRole;
import org.jetbrains.annotations.NotNull;

/**
 * Ressource humaine
 */
public class HumanResource {
    /**
     * Id de la ressource
     */
    private long resourceId;

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
        this.resourceId = resourceId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.role = HumanRole.fromString(role);
        this.description = description;
    }

    /**
     * Constructeur, permet de copier une ressource humaine
     *
     * @param resource Ressource à copier
     */
    public HumanResource(HumanResource resource) {
        resourceId = resource.resourceId;
        firstname = resource.firstname;
        lastname = resource.lastname;
        job = resource.job;
        role = resource.role;
        description = resource.description;
    }

    public long getResourceId() {
        return resourceId;
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

    public String getRole() {
        return role.toString();
    }

    public String toString() {
        return firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase() + " "
                + lastname.toUpperCase() + ", " + role + ", " + job;
    }
}
