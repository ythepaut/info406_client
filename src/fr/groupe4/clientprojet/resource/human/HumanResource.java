package fr.groupe4.clientprojet.resource.human;

import fr.groupe4.clientprojet.resource.human.enums.HumanRole;

/**
 * Ressource humaine
 */
public class HumanResource {
    /**
     * Id de la ressource
     */
    private long idResource;

    /**
     * Prénom
     */
    private String firstname;

    /**
     * Nom
     */
    private String lastname;

    /**
     * Job
     */
    private String job;

    /**
     * Rôle
     */
    private HumanRole role;

    /**
     * Description
     */
    private String description;

    /**
     * Constructeur
     *
     * @param idResource Id
     * @param firstname Prénom
     * @param lastname Nom
     * @param job Job
     * @param role Rôle
     * @param description Description
     */
    public HumanResource(long idResource, String firstname, String lastname, String job, String role, String description) {
        this.idResource = idResource;
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
        idResource = resource.idResource;
        firstname = resource.firstname;
        lastname = resource.lastname;
        job = resource.job;
        role = resource.role;
        description = resource.description;
    }

    public long getIdResource() {
        return idResource;
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
}
