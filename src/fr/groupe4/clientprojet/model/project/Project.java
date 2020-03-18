package fr.groupe4.clientprojet.model.project;

import fr.groupe4.clientprojet.model.project.enums.ProjectStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.*;

public class Project {
    /**
     * ID du projet
     */
    private long id;

    /**
     * Nom du projet
     */
    @NotNull
    private String name;

    /**
     * Description du projet
     */
    @NotNull
    private String description;

    /**
     * Date limite
     */
    @Nullable
    private LocalDateTime deadline;

    /**
     * Status du projet
     */
    @NotNull
    private ProjectStatus status;

    public Project(long id, @NotNull String name, @NotNull String description, long deadline, @NotNull String status) {
        this.id = id;
        this.name = name;
        this.description = description;

        if (deadline == 0) {
            this.deadline = null;
        }
        else {
            this.deadline = Instant.ofEpochMilli(deadline * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        this.status = ProjectStatus.fromString(status);
    }

    @Override
    @NotNull
    public String toString() {
        return id + " - " + name + " - " + description + " - " + getDeadline() + " - " + status;
    }

    public long getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getDescription() {
        return description;
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

    @NotNull
    public ProjectStatus getStatus() {
        return status;
    }
}
