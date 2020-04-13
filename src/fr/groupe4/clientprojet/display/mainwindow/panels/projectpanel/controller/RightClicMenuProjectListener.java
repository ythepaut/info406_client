package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Gestion des clics sur le menu contextuel du clic droit
 */
public class RightClicMenuProjectListener extends MouseAdapter {
    /**
     * ProjectPanel parent
     */
    private ProjectPanel parent;

    /**
     * Constructeur
     *
     * @param parent Parent
     */
    public RightClicMenuProjectListener(ProjectPanel parent) {
        this.parent = parent;
    }

    /**
     * Souris pressée
     *
     * @param e Event
     */
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    /**
     * Souris relâchée
     *
     * @param e Event
     */
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    /**
     * Action
     *
     * @param e Event
     */
    private void doPop(MouseEvent e) {
        ProjectPopupMenu menu = new ProjectPopupMenu(parent);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}