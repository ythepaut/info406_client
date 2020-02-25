package fr.groupe4.clientprojet.calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import fr.groupe4.clientprojet.communication.Communication;

/**
 * Calendrier, modèle
 */
public class Calendar extends Observable {
    Object trueTasks;

    public Calendar(int week, int year) {
        // this.trueTasks = Communication.getWeekTasks(week, year);
        trueTasks = null;

        setChanged();
        notifyObservers();
    }

    public ArrayList<CalendarDayTask>[] getTasksDay(int day) {
        ArrayList<CalendarDayTask>[] tasks = null;

        return tasks;
    }
}
