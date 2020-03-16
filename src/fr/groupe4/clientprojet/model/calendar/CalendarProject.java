package fr.groupe4.clientprojet.model.calendar;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Calendrier
 */
public class CalendarProject {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private int week, year;

    public CalendarProject(int week, int year) {
        this.week = week;
        this.year = year;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
