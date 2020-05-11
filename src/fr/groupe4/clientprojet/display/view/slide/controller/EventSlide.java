package fr.groupe4.clientprojet.display.view.slide.controller;

import fr.groupe4.clientprojet.display.view.slide.enums.SlideMove;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Le listener du slide
 */
public class EventSlide implements ActionListener {
    /**
     * Le panel Slide
     */
    private Slide source;

    /**
     * Le constructeur
     *
     * @param source : le panel Slide
     */
    public EventSlide(Slide source) {
        this.source = source;
    }

    /**
     * Quand un bouton est clické
     *
     * @param e : l'event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(SlideMove.LEFT.getName())) {
            source.setSlide(source.getSlide() -1);
        } else if (command.equals(SlideMove.RIGHT.getName())) {
            source.setSlide(source.getSlide() +1);
        } else {
            ArrayList<String> names = source.getSlideName();
            if (names.contains(command)) {
                int i = 0;
                while (!names.get(i).equals(command)) {
                    i++;
                }
                source.setSlide(i);
            }
        }
    }
}
