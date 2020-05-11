package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view.TaskCreationDialog;
import fr.groupe4.clientprojet.model.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Nouvelle tâche
 */
public class NewTaskListener implements ActionListener {
    /**
     * Projet
     */
    @NotNull
    private final Project project;

    /**
     * Parent
     */
    @NotNull
    private final JPanel parent;

    /**
     * Constructeur
     *
     * @param project Projet
     * @param parent JFrame parente
     */
    public NewTaskListener(@NotNull Project project, @NotNull JPanel parent) {
        this.project = project;
        this.parent = parent;
    }

    /**
     * Clic sur le bouton
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new TaskCreationDialog(parent, project);
    }
}
