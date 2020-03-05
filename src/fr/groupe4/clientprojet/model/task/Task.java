package fr.groupe4.clientprojet.model.task;

import fr.groupe4.clientprojet.model.task.enums.TaskStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Task {
    private long id;
    private String name;
    private String description;
    private TaskStatus status;
    private LocalDate deadline;
    private long projectId;

    public Task(long id, String name, String description, String status, long deadline, long projectId) {
        this(id, name, description, TaskStatus.fromString(status), deadline, projectId);
    }

    public Task(long id, String name, String description, TaskStatus status, long deadline, long projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.deadline = Instant.ofEpochMilli(deadline*1000).atZone(ZoneId.systemDefault()).toLocalDate();
        this.projectId = projectId;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public long getDeadlineAsSeconds() {
        return deadline.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }
}
