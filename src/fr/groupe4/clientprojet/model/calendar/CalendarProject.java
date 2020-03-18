package fr.groupe4.clientprojet.model.calendar;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.model.calendar.enums.CalendarType;
import fr.groupe4.clientprojet.model.timeslot.TimeSlotList;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Calendrier
 *
 * @author Romain
 */
public class CalendarProject {
    /**
     * Gestion des event
     */
    @NotNull
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Valeur de départ, maintenant
     */
    @NotNull
    private LocalDate now;

    /**
     * Date de début
     */
    @NotNull
    private LocalDate from;

    /**
     * Date de fin
     */
    @NotNull
    private LocalDate to;

    /**
     * Type de calendrier
     */
    @NotNull
    private CalendarType type;

    public CalendarProject(@NotNull CalendarType type) throws IllegalArgumentException {
        this.type = type;

        now = LocalDate.now();

        changeFromTo();
    }

    /**
     * Ajuste les variables from et to
     *
     * @throws IllegalArgumentException Type de calendrier inconnu ou pas encore fait
     */
    private void changeFromTo() throws IllegalArgumentException {
        switch (type) {
            case DAY:
                throw new IllegalArgumentException("Pas encore fait : jour");

            case WEEK:
                from = now.with(DayOfWeek.MONDAY);
                to = now.with(DayOfWeek.SUNDAY);
                break;

            case MONTH:
                throw new IllegalArgumentException("Pas encore fait : mois");

            case YEAR:
                throw new IllegalArgumentException("Pas encore fait : année");

            default:
                throw new IllegalArgumentException("Type de calendrier inconnu");
        }
    }

    /**
     * Récupère la liste des créneaux
     *
     * @return Liste des créneaux
     */
    public TimeSlotList getTimeSlots() {
        Communication c = Communication.builder()
                .getUserTimeSlotList(from, to)
                .startNow()
                .sleepUntilFinished()
                .build();

        return (TimeSlotList) c.getResult();
    }

    /**
     * Getter type
     *
     * @return Type de calendrier
     */
    @NotNull
    public CalendarType getType() {
        return type;
    }

    /**
     * Récupère la date de focus
     *
     * @return Date de focus
     */
    @NotNull
    public LocalDate getDate() {
        return now;
    }

    /**
     * Passe au précédent
     */
    public void previous() {
        now = now.minusWeeks(1);
        changeFromTo();
        propertyChangeSupport.firePropertyChange("", null, null);
    }

    /**
     * Passe au suivant
     */
    public void next() {
        now = now.plusWeeks(1);
        changeFromTo();
        propertyChangeSupport.firePropertyChange("", null, null);
    }

    /**
     * Ajoute un observer
     *
     * @param listener Observer
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Retire un observer
     *
     * @param listener Observer
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
