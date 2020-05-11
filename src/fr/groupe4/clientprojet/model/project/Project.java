package fr.groupe4.clientprojet.model.project;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.project.enums.ProjectStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Projet
 */
public class Project {
    /**
     * Taille minimale d'un nom de projet
     */
    public static final int MIN_NAME_LENGTH = 3;

    /**
     * Taille maximale d'un nom de projet
     */
    public static final int MAX_NAME_LENGTH = 255;

    /**
     * ID du projet
     */
    private final long id;

    /**
     * Nom du projet
     */
    @NotNull
    private final String name;

    /**
     * Description du projet
     */
    @NotNull
    private final String description;

    /**
     * Date limite
     */
    @Nullable
    private final LocalDateTime deadline;

    /**
     * Status du projet
     */
    @NotNull
    private final ProjectStatus status;

    /**
     * Constructeur
     *
     * @param id          ID BDD
     * @param name        Nom
     * @param description Description
     * @param deadline    Échéance, nombre de secondes depuis le 01/01/1970 UTC
     * @param status      Statu
     */
    public Project(long id, @NotNull String name, @NotNull String description, long deadline, @NotNull String status) {
        this.id = id;
        this.name = name;
        this.description = description;

        if (deadline == 0) {
            this.deadline = null;
        } else {
            this.deadline = Instant.ofEpochMilli(deadline * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        this.status = ProjectStatus.fromString(status);
    }

    /**
     * Vers chaine
     *
     * @return Chaine
     */
    @Override
    @NotNull
    public String toString() {
        return id + " - " + name + " - " + description + " - " + getDeadline() + " - " + status;
    }

    /**
     * Récupère l'id
     *
     * @return ID
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
     * Récupère la deadline
     *
     * @return Deadline
     */
    @Nullable
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Récupère la deadline sous forme de secondes depuis le 01/01/1970 UTC
     *
     * @return Deadline en secondes
     * @throws IllegalStateException S'il n'y a pas de deadline
     */
    public long getDeadlineAsSeconds() throws IllegalStateException {
        if (deadline == null) {
            Logger.error("Deadline null, essai de conversion vers secondes");
            throw new IllegalStateException("Méthode impossible à exécuter lorsque la deadline est null");
        } else {
            return deadline.atZone(ZoneId.systemDefault()).toEpochSecond();
        }
    }

    /**
     * Récupère le status
     *
     * @return Status
     */
    @NotNull
    public ProjectStatus getStatus() {
        return status;
    }
}
