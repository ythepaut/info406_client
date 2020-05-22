package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.MaterielGestionDialog;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view.UsersGestionDialog;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;
import fr.groupe4.clientprojet.model.project.Project;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener du panel de Projet
 */
public class EventProjectPanel implements ActionListener {
    public static final String NEWUSERS = "newusers";
    public static final String NEWMAT = "newmat";

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     * Constructeur
     *
     * @param project Projet
     */
    public EventProjectPanel(@NotNull Project project) {
        this.project = project;
    }

    /**
     * Bouton cliqué
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (NEWUSERS.equals(e.getActionCommand())) {
            new UsersGestionDialog(MainWindow.getInstance(), project);
        } else {
            new MaterielGestionDialog(MainWindow.getInstance());
        }
    }
}
