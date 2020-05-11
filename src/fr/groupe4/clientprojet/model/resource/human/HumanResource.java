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
    private final String firstname;

    /**
     * Nom
     */
    @NotNull
    private final String lastname;

    /**
     * Job
     */
    @NotNull
    private final String job;

    /**
     * Rôle
     */
    @NotNull
    private final HumanRole role;

    /**
     * Description
     */
    @NotNull
    private final String description;

    /**
     * Constructeur
     *
     * @param resourceId  Id
     * @param firstname   Prénom
     * @param lastname    Nom
     * @param job         Job
     * @param role        Rôle
     * @param description Description
     */
    public HumanResource(long resourceId,
                         @NotNull String firstname,
                         @NotNull String lastname,
                         @NotNull String job,
                         @NotNull String role,
                         @NotNull String description) {
        super(ResourceType.HUMAN_RESOURCE, resourceId);

        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.role = HumanRole.fromString(role);
        this.description = description;
    }

    /**
     * Permet de copier une ressource humaine<br>
     * Utile pour l'utilisateur par exemple, où une RH lui est fournie
     */
    public HumanResource(HumanResource toCopy) {
        this(
                toCopy.getResourceId(),
                toCopy.firstname,
                toCopy.lastname,
                toCopy.job,
                toCopy.role.toString(),
                toCopy.description);
    }

    /**
     * Récupère le prénom
     *
     * @return Prénom
     */
    @NotNull
    public String getFirstname() {
        return firstname;
    }

    /**
     * Récupère le nom de famille
     *
     * @return Nom
     */
    @NotNull
    public String getLastname() {
        return lastname;
    }

    /**
     * Récupère le nom complet
     *
     * @return Nom
     */
    @NotNull
    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }

    /**
     * Récupère le job
     *
     * @return Job
     */
    @NotNull
    public String getJob() {
        return job;
    }

    /**
     * Récupère la description
     *
     * @return Description
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    /**
     * Récupère le rôle
     *
     * @return Rôle
     */
    @NotNull
    public HumanRole getRole() {
        return role;
    }

    /**
     * Vers String
     *
     * @return String
     */
    @NotNull
    @Override
    public String toString() {
        return firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase() + " "
                + lastname.toUpperCase() + ", " + role + ", " + job;
    }
}
