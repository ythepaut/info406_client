package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RightClicMenuProjectListener extends MouseAdapter {
    private ProjectPanel parent;

    public RightClicMenuProjectListener(ProjectPanel parent) {
        this.parent = parent;
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e) {
        ProjectPopupMenu menu = new ProjectPopupMenu(parent, parent.getImportantComponent());
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}