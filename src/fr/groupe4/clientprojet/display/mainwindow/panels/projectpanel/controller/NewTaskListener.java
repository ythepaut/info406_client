package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view.TaskCreationDialog;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;
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
     * Constructeur
     *
     * @param project Projet
     */
    public NewTaskListener(@NotNull Project project) {
        this.project = project;
    }

    /**
     * Clic sur le bouton
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new TaskCreationDialog(MainWindow.getInstance(), project);
    }
}
