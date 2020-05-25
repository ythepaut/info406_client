package fr.groupe4.clientprojet.model.resource.human;

import fr.groupe4.clientprojet.model.resource.ResourceState;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Ressource humaine dans le contexte d'un projet
 */
public class HumanResourceProject extends HumanResource {
    /**
     * Id du projet
     */
    private final long idProject;

    /**
     * Date de début de l'allocation
     */
    @NotNull
    private final LocalDateTime dateStart;

    /**
     * Date de fin de l'allocation
     */
    @NotNull
    private final LocalDateTime dateEnd;

    /**
     * Id de l'utilisateur ayant demandé cette ressource
     */
    private final long idIssuer;

    /**
     * État de l'allocation
     */
    @NotNull
    private final ResourceState status;

    /**
     * Id BDD
     */
    private final long allocationId;

    public HumanResourceProject(@NotNull HumanResource humanResource,
                                long idProject,
                                long dateStart,
                                long dateEnd,
                                long idIssuer,
                                @NotNull String status,
                                long allocationId) {
        super(humanResource);

        this.idProject = idProject;
        this.dateStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateStart*1000), ZoneId.systemDefault());
        this.dateEnd = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateEnd*1000), ZoneId.systemDefault());
        this.idIssuer = idIssuer;
        this.status = ResourceState.getEnum(status);
        this.allocationId = allocationId;
    }

    /**
     * Getter de l'id du projet
     *
     * @return Id du projet
     */
    public long getIdProject() {
        return idProject;
    }

    /**
     * Getter de l'id du demandeur
     *
     * @return Id du demandeur
     */
    public long getIdIssuer() {
        return idIssuer;
    }

    /**
     * Récupère la date de début
     *
     * @return Date de début
     */
    @NotNull
    public LocalDateTime getDateStart() {
        return dateStart;
    }

    /**
     * Récupère la date de fin
     *
     * @return Date de fin
     */
    @NotNull
    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    /**
     * Récupère le status
     *
     * @return Status
     */
    @NotNull
    public ResourceState getStatus() {
        return status;
    }

    /**
     * Récupère l'id d'allocation
     *
     * @return Id d'allocation
     */
    public long getAllocationId() {
        return allocationId;
    }
}
