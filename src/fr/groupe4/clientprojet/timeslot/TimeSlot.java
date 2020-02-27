package fr.groupe4.clientprojet.timeslot;

import java.util.Date;

public class TimeSlot {
    private long id;
    private long startTime;
    private long endTime;
    private long idTask;
    private long roomId;

    public TimeSlot(long id, long startTime, long endTime, long idTask, long roomId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.idTask = idTask;
        this.roomId = roomId;
    }

    public Date getStartTime() {
        return new Date(startTime*1000);
    }

    public Date getEndTime() {
        return new Date(endTime*1000);
    }

    public String toString() {
        return getStartTime() + ":" + getEndTime();
    }
}
