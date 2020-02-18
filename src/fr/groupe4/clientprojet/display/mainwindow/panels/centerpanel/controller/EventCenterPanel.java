package fr.groupe4.clientprojet.display.mainwindow.panels.centerpanel.controller;

import fr.groupe4.clientprojet.display.mainwindow.panels.centerpanel.view.CenterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener du panel central
 */
public class EventCenterPanel implements ActionListener {
    /**
     * Le panel central
     */
    private CenterPanel owner;

    /**
     * Variables statiques pour les boutons
     * destinés à disparaître au profit d'une énumeration
     */
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String HOME = "0";
    public static final String TASK = "1";
    public static final String MESSAGE = "2";

    /**
     * Le constructeur
     *
     * @param owner : le panel central
     */
    public EventCenterPanel(CenterPanel owner) {
        this.owner = owner;
    }

    /**
     * Quand un bouton est cliqué
     *
     * @param e : l'event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case LEFT:
                owner.setSlide(owner.getSlide() -1);
                break;

            case RIGHT:
                owner.setSlide(owner.getSlide() +1);
                break;

            case HOME:
                owner.setSlide(0);
                break;

            case TASK:
                owner.setSlide(1);
                break;

            case MESSAGE:
                owner.setSlide(2);
                break;

            default:
        }
        owner.redraw();
    }
}
