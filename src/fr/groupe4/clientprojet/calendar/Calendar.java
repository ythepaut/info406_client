package fr.groupe4.clientprojet.calendar;

import java.util.Observable;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.communication.TaskList;

/**
 * Calendrier, modèle
 */
public class Calendar extends Observable {
    public Calendar() {
        TaskList tasks = Communication.getTasks();

        setChanged();
        notifyObservers();
    }
}
