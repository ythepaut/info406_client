package fr.groupe4.clientprojet.model.timeslot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeSlot {
    public static final int LOWEST_TIME = LocalTime.of(7, 0).toSecondOfDay();
    public static final int HIGHEST_TIME = LocalTime.of(20, 0).toSecondOfDay();
    public static final int TIME_SCALE = LocalTime.of(0, 15).toSecondOfDay();

    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long idTask;
    private long roomId;

    public TimeSlot(long id, long startTime, long endTime, long idTask, long roomId) {
        this.id = id;
        this.startTime = Instant.ofEpochMilli((startTime/TIME_SCALE)*TIME_SCALE*1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.endTime = Instant.ofEpochMilli((endTime/TIME_SCALE)*TIME_SCALE*1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.idTask = idTask;
        this.roomId = roomId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean sameDay() {
        return startTime.toLocalDate().isEqual(endTime.toLocalDate());
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "from "
                + startTime.getDayOfMonth() + " " + startTime.getMonth() + " " + startTime.getYear()
                + " at " + startTime.getHour() + ":" + String.format("%02d", startTime.getMinute()) + ":" + String.format("%02d", startTime.getSecond())
                + " to "
                + endTime.getDayOfMonth() + " " + endTime.getMonth() + " " + endTime.getYear()
                + " at " + endTime.getHour() + ":" + String.format("%02d", endTime.getMinute()) + ":" + String.format("%02d", endTime.getSecond());
    }
}
