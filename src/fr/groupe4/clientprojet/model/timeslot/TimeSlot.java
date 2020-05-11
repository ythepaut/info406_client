package fr.groupe4.clientprojet.model.timeslot;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Créneau
 */
public class TimeSlot {
    /**
     * Heure minimale dans une journée
     */
    public static final int LOWEST_TIME = LocalTime.of(7, 0).toSecondOfDay();

    /**
     * Heure maximale dans une journée
     */
    public static final int HIGHEST_TIME = LocalTime.of(20, 0).toSecondOfDay();

    /**
     * Échelle de temps (pas minimal)
     */
    public static final int TIME_SCALE = LocalTime.of(0, 15).toSecondOfDay();

    /**
     * Id du créneau
     */
    private final long id;

    /**
     * Temps de début
     */
    @NotNull
    private final LocalDateTime startTime;

    /**
     * Temps de fin
     */
    @NotNull
    private final LocalDateTime endTime;

    /**
     * Id de la tâche associée
     */
    private final long taskId;

    /**
     * Id de la salle associée
     */
    private final long roomId;

    /**
     * Constructeur
     *
     * @param id Id BDD
     * @param startTime Temps de début en secondes depuis le 01/01/1970 UTC
     * @param endTime Temps de fin en secondes aussi
     * @param taskId Id de la tâche
     * @param roomId Id de la salle
     */
    public TimeSlot(long id, long startTime, long endTime, long taskId, long roomId) {
        this.id = id;

        this.startTime = Instant.ofEpochMilli((startTime/TIME_SCALE)*TIME_SCALE*1000)
                                .atZone(ZoneId.systemDefault()).toLocalDateTime();

        this.endTime = Instant.ofEpochMilli((endTime/TIME_SCALE)*TIME_SCALE*1000)
                              .atZone(ZoneId.systemDefault()).toLocalDateTime();

        this.taskId = taskId;
        this.roomId = roomId;
    }

    /**
     * Retourne l'id du créneau
     *
     * @return Id
     */
    public long getId() {
        return id;
    }

    /**
     * Récupère la date de début
     *
     * @return Date de début
     */
    @NotNull
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Récupère la date de fin
     *
     * @return Date de fin
     */
    @NotNull
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Si la date de début et de fin est le même jour
     *
     * @return Si même jour
     */
    public boolean sameDay() {
        return startTime.toLocalDate().isEqual(endTime.toLocalDate());
    }

    /**
     * Récupère l'id de la tâche
     *
     * @return Id de la tâche
     */
    public long getTaskId() {
        return taskId;
    }

    /**
     * Récupère l'id de la salle
     *
     * @return Id de la salle
     */
    public long getRoomId() {
        return roomId;
    }

    /**
     * Vers String
     *
     * @return String
     */
    @NotNull
    @Override
    public String toString() {
        return "from "
                    + startTime.getDayOfMonth() + " "
                    + startTime.getMonth() + " "
                    + startTime.getYear()
                + " at "
                    + startTime.getHour() + ":"
                    + String.format("%02d", startTime.getMinute()) + ":"
                    + String.format("%02d", startTime.getSecond())
                + " to "
                    + endTime.getDayOfMonth() + " "
                    + endTime.getMonth() + " "
                    + endTime.getYear()
                + " at "
                    + endTime.getHour() + ":"
                    + String.format("%02d", endTime.getMinute()) + ":"
                    + String.format("%02d", endTime.getSecond())
                ;
    }
}
