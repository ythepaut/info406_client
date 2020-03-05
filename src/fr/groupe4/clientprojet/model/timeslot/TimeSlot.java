package fr.groupe4.clientprojet.model.timeslot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeSlot {
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long idTask;
    private long roomId;

    public TimeSlot(long id, long startTime, long endTime, long idTask, long roomId) {
        this.id = id;
        this.startTime = Instant.ofEpochMilli(startTime*1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.endTime = Instant.ofEpochMilli(endTime*1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.idTask = idTask;
        this.roomId = roomId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
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
