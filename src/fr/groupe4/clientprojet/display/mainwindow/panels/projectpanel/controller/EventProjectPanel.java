package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.MaterielGestionDialog;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view.UsersGestionDialog;
import fr.groupe4.clientprojet.model.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener du panel de Projet
 */
public class EventProjectPanel implements ActionListener {
    public static final String NEWUSERS = "newusers";
    public static final String NEWMAT = "newmat";

    /**
     * Panel qui fait les appels
     */
    @NotNull
    private final JPanel source;

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     * Constructeur
     *
     * @param source Panel qui fait les appels
     */
    public EventProjectPanel(@NotNull JPanel source, @NotNull Project project) {
        this.source = source;
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
          new UsersGestionDialog(source, project);
        } else {
            new MaterielGestionDialog(source);
        }
    }
}
