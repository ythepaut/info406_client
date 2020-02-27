package fr.groupe4.clientprojet.calendar;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

import fr.groupe4.clientprojet.communication.Communication;

/**
 * Calendrier, modèle
 */
public class CalendarProject {
    private Object trueTasks;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public CalendarProject(int week, int year) {
        // this.trueTasks = Communication.getWeekTasks(week, year);
        trueTasks = null;

        propertyChangeSupport.firePropertyChange("todo", null, null);
    }

    public ArrayList<CalendarDayTask>[] getTasksDay(int day) {
        ArrayList<CalendarDayTask>[] tasks = null;

        return tasks;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
