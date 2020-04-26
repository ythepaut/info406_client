package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.MaterielGestionDialog;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view.UsersGestionDialog;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener du panel de Projet
 */
public class EventProjectPanel implements ActionListener {
    public static final String NEWUSERS = "newusers";
    public static final String NEWMAT = "newmat";

    /**
     * Le panel qui fait les appels
     */
    private JPanel source;
    private long projectId;

    /**
     * Le constructeur
     *
     * @param source : le panel qui fait les appels
     */
    public EventProjectPanel(JPanel source, long projectId){
        this.source = source;
        this.projectId = projectId;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (NEWUSERS.equals(e.getActionCommand())) {
          new UsersGestionDialog(source, projectId);
        } else {
            new MaterielGestionDialog(source);
        }
    }
}
