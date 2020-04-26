package fr.groupe4.clientprojet.display.view.calendar.controller;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.calendar.CalendarProject;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CalendarKeyAdapter extends KeyAdapter {
    /**
     * Calendrier
     */
    @NotNull
    private CalendarProject calendar;

    public CalendarKeyAdapter(@NotNull CalendarProject calendar) {
        this.calendar = calendar;
    }

    /**
     * Touche pressée
     *
     * @param e Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO

        Logger.debug("Hmmm c'est pas censé fonctionner pourtant...");

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
