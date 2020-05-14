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
    private long idProject;

    /**
     * Date de début de l'allocation
     */
    @NotNull
    private LocalDateTime dateStart;

    /**
     * Date de fin de l'allocation
     */
    @NotNull
    private LocalDateTime dateEnd;

    /**
     * Id de l'utilisateur ayant demandé cette ressource
     */
    private long idIssuer;

    /**
     * État de l'allocation
     */
    @NotNull
    private ResourceState status;

    public HumanResourceProject(@NotNull HumanResource humanResource,
                                long idProject,
                                long dateStart,
                                long dateEnd,
                                long idIssuer,
                                @NotNull String status) {
        super(humanResource);

        this.idProject = idProject;
        this.dateStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateStart*1000), ZoneId.systemDefault());
        this.dateEnd = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateEnd*1000), ZoneId.systemDefault());
        this.idIssuer = idIssuer;
        this.status = ResourceState.getEnum(status);
    }

    public long getIdProject() {
        return idProject;
    }

    public long getIdIssuer() {
        return idIssuer;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public ResourceState getStatus() {
        return status;
    }
}
