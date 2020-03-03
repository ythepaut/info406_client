package fr.groupe4.clientprojet.calendar;

import java.time.LocalTime;

/**
 * Tâche quotidienne du calendrier
 * Une tâche peut en effet s'étendre sur plusieurs jours (ex : vacances),
 * voire commencer avant la période, on sépare donc cette tâche en sous-tâches quotidiennes
 *
 * @author Romain
 */
public class CalendarDayTask {
    /**
     * Heure de début
     */
    private LocalTime startTime;

    /**
     * Heure de fin
     */
    private LocalTime endTime;

    /**
     * Journée complète ou non
     * Si oui, startTime et endTime n'importent pas
     */
    private boolean fullDay;

    /**
     * Est-ce que la tâche a débuté aujourd'hui ou depuis quelques jours déjà ?
     */
    private boolean isStart;

    /**
     * Est-ce que la tâche se termine aujourd'hui ou dans quelques jours ?
     */
    private boolean isEnd;

    public CalendarDayTask() {

    }


}
