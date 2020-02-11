package fr.groupe4.clientprojet.calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.communication.TaskList;

/**
 * Calendrier, modèle
 */
public class Calendar extends Observable {
    TaskList trueTasks;

    public Calendar(int week, int year) {
        try {
            this.trueTasks = Communication.getWeekTasks(week, year);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers();
    }

    public ArrayList<CalendarDayTask>[] getTasksDay(int day) {
        ArrayList<CalendarDayTask>[] tasks = null;

        return tasks;
    }
}
