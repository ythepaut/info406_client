package fr.groupe4.clientprojet.model.task;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.task.enums.TaskStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Tâche
 */
public class Task {
    /**
     * Id de la tâche
     */
    private final long id;

    /**
     * Nom de la tâche
     */
    @NotNull
    private final String name;

    /**
     * Description de la tâche
     */
    @NotNull
    private final String description;

    /**
     * État de la tâche
     */
    @NotNull
    private final TaskStatus status;

    /**
     * Deadline
     */
    @Nullable
    private final LocalDateTime deadline;

    /**
     * Id du projet associé
     */
    private final long projectId;

    /**
     * Constructeur
     *
     * @param id Id
     * @param name Nom
     * @param description description
     * @param status Status
     * @param deadline Deadline en secondes depuis le 01/01/1970 UTC
     * @param projectId Id du projet associé
     */
    public Task(long id,
                @NotNull String name,
                @NotNull String description,
                @NotNull String status,
                long deadline,
                long projectId) {
        this(id, name, description, TaskStatus.fromString(status), deadline, projectId);
    }

    /**
     * Constructeur
     *
     * @param id Id
     * @param name Nom
     * @param description description
     * @param status Status
     * @param deadline Deadline en secondes depuis le 01/01/1970 UTC
     * @param projectId Id du projet associé
     */
    public Task(long id,
                @NotNull String name,
                @NotNull String description,
                @NotNull TaskStatus status,
                long deadline,
                long projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;

        if (deadline == 0) {
            this.deadline = null;
        }
        else {
            this.deadline = Instant.ofEpochMilli(deadline*1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        this.projectId = projectId;
    }

    /**
     * Récupère l'id
     *
     * @return Id
     */
    public long getId() {
        return id;
    }

    /**
     * Récupère le nom
     *
     * @return Nom
     */
    @NotNull
    public String getName() {
        return name;
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
     * Récupère le status
     *
     * @return Status
     */
    @NotNull
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Récupère la deadline
     *
     * @return Deadline
     */
    @Nullable
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Récupère la deadline en secondes depuis le 01/01/1970 UTC
     *
     * @return Nombre de secondes
     *
     * @throws IllegalStateException S'il n'y a pas de deadline
     */
    public long getDeadlineAsSeconds() throws IllegalStateException {
        if (deadline == null) {
            Logger.error("Deadline null");
            throw new IllegalStateException("Méthode impossible à exécuter lorsque la deadline est null");
        }
        else {
            return deadline.atZone(ZoneId.systemDefault()).toEpochSecond();
        }
    }

    /**
     * Récupère l'id du projet associé
     *
     * @return Id
     */
    public long getProjectId() {
        return projectId;
    }
}
