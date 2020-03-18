package fr.groupe4.clientprojet.display.view.calendar.controller;

import fr.groupe4.clientprojet.model.calendar.CalendarProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boutons précédent et suivant
 *
 * @author Romain
 */
public class CalendarButtonListener implements ActionListener {
    /**
     * Mode avant
     */
    public static final int PREVIOUS = 0;

    /**
     * Mode arrière
     */
    public static final int NEXT = 1;

    /**
     * Mode
     */
    private int action;

    /**
     * Calendrier à changer
     */
    private CalendarProject calendar;

    /**
     * Constructeur
     *
     * @param calendar Calendrier
     * @param action Que faire
     */
    public CalendarButtonListener(CalendarProject calendar, int action) {
        this.calendar = calendar;
        this.action = action;
    }

    /**
     * Action effectuée
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (action == PREVIOUS) {
            calendar.previous();
        }
        else {
            calendar.next();
        }
    }
}
