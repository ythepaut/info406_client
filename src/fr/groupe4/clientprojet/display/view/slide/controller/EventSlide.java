package fr.groupe4.clientprojet.display.view.slide.controller;

import fr.groupe4.clientprojet.display.view.slide.SlideItem;
import fr.groupe4.clientprojet.display.view.slide.enums.SlideMove;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Listener du slide
 */
public class EventSlide implements ActionListener {
    /**
     * Panel Slide
     */
    @NotNull
    private final Slide source;

    /**
     * Constructeur
     *
     * @param source Panel Slide
     */
    public EventSlide(@NotNull Slide source) {
        this.source = source;
    }

    /**
     * Quand un bouton est cliqué
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(SlideMove.LEFT.getName())) {
            source.setSlide(source.getSlide() -1);
        } else if (command.equals(SlideMove.RIGHT.getName())) {
            source.setSlide(source.getSlide() +1);
        } else {
            ArrayList<SlideItem> slides = source.getSlideItems();

            int i = 0;

            for (SlideItem slide : slides) {
                if (slide.getName().equals(command)) {
                    source.setSlide(i);
                }
                else {
                    i++;
                }
            }

        }
    }
}
