package fr.groupe4.clientprojet.model.project;

import fr.groupe4.clientprojet.model.project.enums.ProjectStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Project {
    private long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private ProjectStatus status;

    public Project(long id, String name, String description, long deadline, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = Instant.ofEpochMilli(deadline*1000).atZone(ZoneId.systemDefault()).toLocalDate();
        this.status = ProjectStatus.fromString(status);
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + description + " - " + getDeadline() + " - " + status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public long getDeadlineAsSeconds() {
        return deadline.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    public ProjectStatus getStatus() {
        return status;
    }
}
