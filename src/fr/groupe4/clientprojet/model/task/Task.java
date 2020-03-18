package fr.groupe4.clientprojet.model.task;

import fr.groupe4.clientprojet.model.task.enums.TaskStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Task {
    /**
     * Id de la tâche
     */
    private long id;

    /**
     * Nom de la tâche
     */
    @NotNull
    private String name;

    /**
     * Description de la tâche
     */
    @NotNull
    private String description;

    /**
     * État de la tâche
     */
    @NotNull
    private TaskStatus status;

    /**
     * Deadline
     */
    @Nullable
    private LocalDateTime deadline;

    /**
     * Id du projet asssocié
     */
    private long projectId;

    public Task(long id, String name, String description, String status, long deadline, long projectId) {
        this(id, name, description, TaskStatus.fromString(status), deadline, projectId);
    }

    public Task(long id, String name, String description, TaskStatus status, long deadline, long projectId) {
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

    public long getId() {
        return id;
    }

    @Nullable
    public LocalDateTime getDeadline() {
        return deadline;
    }

    public long getDeadlineAsSeconds() throws IllegalStateException {
        if (deadline == null) {
            throw new IllegalStateException("Méthode impossible à exécuter lorsque la deadline est null");
        }
        else {
            return deadline.atZone(ZoneId.systemDefault()).toEpochSecond();
        }
    }

    public String getName(){return name;}

    public String getDescription(){return description;}
}
