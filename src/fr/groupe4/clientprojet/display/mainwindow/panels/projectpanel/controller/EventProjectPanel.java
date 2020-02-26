package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.enums.ProjectSlide;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventProjectPanel implements ActionListener {
    public static final String LEFT = "left", RIGHT = "right";
    private ProjectPanel source;

    public EventProjectPanel(ProjectPanel source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String slide = e.getActionCommand();
        switch (slide) {
            case LEFT:
                source.setSlide(source.getSlide().getNb() -1);
                break;

            case RIGHT:
                source.setSlide(source.getSlide().getNb() +1);
                break;

            default:
                if (slide.equals(ProjectSlide.HOME.getNbString())) {
                    source.setSlide(ProjectSlide.HOME.getNb());
                } else if (slide.equals(ProjectSlide.TASK.getNbString())) {
                    source.setSlide(ProjectSlide.TASK.getNb());
                } else if (slide.equals(ProjectSlide.MESSAGE.getNbString())) {
                    source.setSlide(ProjectSlide.MESSAGE.getNb());
                }
        }

        source.redraw();
    }
}
