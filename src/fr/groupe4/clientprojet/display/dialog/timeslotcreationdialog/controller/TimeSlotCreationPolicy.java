package fr.groupe4.clientprojet.display.dialog.timeslotcreationdialog.controller;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import fr.groupe4.clientprojet.model.timeslot.TimeSlot;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Permet de mettre des limites sur l'heure (ex : entre 9h et 17h seulement)
 */
public class TimeSlotCreationPolicy implements TimeVetoPolicy {
    /**
     * Pour comparer from et to, avant ou après ?
     */
    public static final boolean BEFORE = true, AFTER = false;

    /**
     * Récérence, cf. BEFORE et AFTER
     */
    private final boolean reference;

    /**
     * Autre picker à comparer
     */
    @NotNull
    private final TimePicker otherPicker;

    /**
     * Constructeur
     *
     * @param reference Référence, si avant ou après
     * @param otherPicker Autre picker pour comparer
     */
    public TimeSlotCreationPolicy(boolean reference, @NotNull TimePicker otherPicker) {
        this.reference = reference;
        this.otherPicker = otherPicker;
    }

    /**
     * Temps valide ou non
     *
     * @param time Temps à vérifier
     *
     * @return Valide ou non
     */
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
