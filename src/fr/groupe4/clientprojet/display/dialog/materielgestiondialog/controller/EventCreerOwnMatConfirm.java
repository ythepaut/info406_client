package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.CreerOwnMatDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.material.MaterialResource;
import fr.groupe4.clientprojet.model.resource.material.MaterialResourceList;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventCreerOwnMatConfirm implements ActionListener {
    /**
     * Parent
     */
    @NotNull
    private final DrawDialog parent;

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     * Nom et description du projet
     */
    private final JTextField nameTextField;
    private final JTextArea descriptionTextArea;

    /**
     *
     * @param parent  Dialog parent
     * @param project Projet courant
     * @param nameTextField       TextField du nom
     * @param descriptionTextArea TextArea de la description
     */
    public EventCreerOwnMatConfirm(CreerOwnMatDialog parent, @NotNull Project project, JTextField nameTextField, JTextArea descriptionTextArea) {
        this.project = project;
        this.parent = parent;
        this.nameTextField = nameTextField;
        this.descriptionTextArea = descriptionTextArea;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String description = descriptionTextArea.getText();
        Communication comm = Communication.builder()
                            .createMaterialResource(name, description).startNow().sleepUntilFinished()
                            .build();
        MaterialResource m = (MaterialResource) comm.getResult();
        comm = Communication.builder()
                            .addMaterialResourceToProject(project.getId(), m.getResourceId(), null, null).startNow().sleepUntilFinished()
                            .build();

        new ErrorDialog("Matériels bien crée et ajouté", "SUCCESS", ErrorDialog.COLOR_OK, parent);
        parent.dispose();
    }
}
