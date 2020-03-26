package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.dialog.matadddialog.view.MatAddDialog;
import fr.groupe4.clientprojet.display.dialog.usersadddialog.view.UsersAddDialog;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener du panel de Projet
 */
public class EventProjectPanel implements ActionListener {

    /**
     * Le panel qui fait les appels
     */
    private ProjectPanel source;
    public static final String NEWUSERS = "newusers";
    public static final String NEWMAT = "newmat";

    /**
     * Le constructeur
     *
     * @param source : le panel qui fait les appels
     */
    public EventProjectPanel(ProjectPanel source){
        this.source = source;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (NEWUSERS.equals(e.getActionCommand())) {
            new UsersAddDialog(source);
        } else {
            new MatAddDialog(source);
        }
    }
}
