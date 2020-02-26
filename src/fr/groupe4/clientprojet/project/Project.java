package fr.groupe4.clientprojet.project;

import fr.groupe4.clientprojet.project.enums.ProjectStatus;

public class Project {
    private long id;
    private String name;
    private String description;
    private long deadline;
    private ProjectStatus status;

    public Project(long id, String name, String description, long deadline, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = ProjectStatus.fromString(status);
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + description + " - " + deadline + " - " + status;
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

    public long getDeadline() {
        return deadline;
    }

    public ProjectStatus getStatus() {
        return status;
    }
}
