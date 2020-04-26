package fr.groupe4.clientprojet.display.dialog.projectcreationdialog.controller;

import com.github.lgooddatepicker.components.DatePicker;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import fr.groupe4.clientprojet.display.dialog.projectcreationdialog.view.ProjectCreationDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.project.enums.ProjectStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EventProjectCreation implements ActionListener {
    private ProjectCreationDialog source;
    private DatePicker dateModel;
    private JTextField nomTextField;
    private JTextArea descriptionTextArea;

    public EventProjectCreation(ProjectCreationDialog source, DatePicker dateModel, JTextField nomTextField, JTextArea descriptionTextArea) {
        this.source = source;
        this.dateModel = dateModel;
        this.nomTextField = nomTextField;
        this.descriptionTextArea = descriptionTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        LocalDate date = dateModel.getDate();
        String nom = nomTextField.getText();
        String description = descriptionTextArea.getText();

        LocalDate deadline = null;

        if (!nom.isBlank() && nom.length() >= 3 && nom.length() < 255) {
            Communication c = Communication.builder()
                    .createProject(nom, description, date, ProjectStatus.ONGOING)
                    .build();

            new LoadDialog(c);

            switch (c.getHTTPCode()) {
                case HTTP_FORBIDDEN:
                    // Pas les permissions de créer un projet
                    new ErrorDialog("Vous n'avez pas les permissions nécessaires");
                    break;

                case HTTP_OK:
                    // Projet créé
                    new ErrorDialog("Projet créé", "SUCCESS", new Color(0, 127, 0));
                    source.dispose();
                    break;

                case HTTP_BAD_REQUEST:
                    new ErrorDialog("Un projet avec ce nom existe déjà");
                    break;

                default:
                    Logger.error("Code invalide :", c);
                    break;
            }
        }
        else {
            // Si le nom n'est pas conforme
            new ErrorDialog("Nom de projet invalide");
        }
    }
}
