package fr.groupe4.clientprojet.display.dialog.timeslotcreationdialog.controller;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.timeslot.TimeSlot;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeSlotCreationPolicy implements TimeVetoPolicy {
    public static final boolean BEFORE = true;
    public static final boolean AFTER = false;

    private final boolean reference;
    private final TimePicker otherPicker;

    public TimeSlotCreationPolicy(boolean reference, TimePicker otherPicker) {
        this.reference = reference;
        this.otherPicker = otherPicker;
    }

    @Override
    public boolean isTimeAllowed(LocalTime time) {
        boolean ok = PickerUtilities.isLocalTimeInRange(
                time,
                LocalTime.ofInstant(Instant.ofEpochMilli(TimeSlot.LOWEST_TIME * 1000), (ZoneId.systemDefault())),
                LocalTime.ofInstant(Instant.ofEpochMilli(TimeSlot.HIGHEST_TIME * 1000), (ZoneId.systemDefault())),
                true);

        if (ok) {
            LocalTime otherTime = otherPicker.getTime();

            if (otherTime == null) {
                return true;
            }
            else {
                if (reference == BEFORE) {
                    return time.isBefore(otherTime);
                } else {
                    return time.isAfter(otherTime);
                }
            }
        }
        else {
            return false;
        }
    }
}
