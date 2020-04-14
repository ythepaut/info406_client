package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view.TaskCreationDialog;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Nouvelle tâche
 */
public class NewTaskListener implements ActionListener {
    /**
     * Id du projet
     */
    private long projectId;

    /**
     * Parent
     */
    @Nullable
    private JFrame parent;

    /**
     * Constructeur
     *
     * @param projectId Id du projet
     *
     * @deprecated À remplacer par le constructeur avec JFrame
     */
    @Deprecated
    public NewTaskListener(long projectId) {
        this(projectId, null);
    }

    /**
     * Constructeur
     *
     * @param projectId Id du projet
     * @param parent JFrame parente
     */
    public NewTaskListener(long projectId, @Nullable JFrame parent) {
        this.projectId = projectId;
        this.parent = parent;
    }

    /**
     * Clic sur le bouton
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new TaskCreationDialog(parent, projectId);
    }
}
