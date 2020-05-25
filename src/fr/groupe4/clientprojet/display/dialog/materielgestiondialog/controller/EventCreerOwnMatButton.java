package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller;

import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.MaterielGestionDialog;
import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.CreerOwnMatDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.material.MaterialResourceList;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventCreerOwnMatButton implements ActionListener {

    /**
     * Parent
     */
    @NotNull
    private final DrawDialog source;

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     *
     * @param source Dialog source
     * @param project Project courant
     */
    public EventCreerOwnMatButton(MaterielGestionDialog source, @NotNull Project project) {
        this.project = project;
        this.source = source;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new CreerOwnMatDialog(source, project);
    }
}
