package fr.groupe4.clientprojet.display.view.calendar.controller;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.calendar.CalendarProject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CalendarKeyAdapter extends KeyAdapter {
    private CalendarProject calendar;

    public CalendarKeyAdapter(CalendarProject calendar) {
        this.calendar = calendar;
    }

    /**
     * Touche pressée
     *
     * @param e Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Logger.debug("Ça marche pas");

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                calendar.previous();
                break;

            case KeyEvent.VK_RIGHT:
                calendar.next();
                break;

            default:
                break;
        }
    }
}
