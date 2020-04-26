package fr.groupe4.clientprojet.display.dialog.taskcreationdialog.controller;

import com.github.lgooddatepicker.components.DatePicker;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view.TaskCreationDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.task.enums.TaskStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EventTaskCreation implements ActionListener {
    private TaskCreationDialog parent;
    private DatePicker dateModel;
    private JTextField nameTextField;
    private JTextArea descriptionTextArea;
    private long projectId;

    public EventTaskCreation(TaskCreationDialog parent, DatePicker dateModel, JTextField nameTextField, JTextArea descriptionTextArea, long projectId) {
        this.parent = parent;
        this.dateModel = dateModel;
        this.nameTextField = nameTextField;
        this.descriptionTextArea = descriptionTextArea;
        this.projectId = projectId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LocalDate date = dateModel.getDate();
        String nom = nameTextField.getText();
        String description = descriptionTextArea.getText();

        if (!nom.isBlank() && nom.length() >= 3 && nom.length() < 255) {
            Communication c = Communication.builder()
                    .createTask(nom, description, TaskStatus.ONGOING, date, projectId)
                    .build();

            new LoadDialog(c);

            switch (c.getHTTPCode()) {
                case HTTP_FORBIDDEN:
                    // Pas les permissions de créer un projet
                    new ErrorDialog("Vous n'avez pas les permissions nécessaires");
                    break;

                case HTTP_OK:
                    // Projet créé
                    new ErrorDialog("Tâche créée", "SUCCESS", new Color(0, 127, 0));
                    parent.dispose();
                    break;

                case HTTP_BAD_REQUEST:
                    new ErrorDialog("Une tâche avec ce nom existe déjà");
                    break;

                default:
                    Logger.error("Code invalide :", c);
                    break;
            }
        }
        else {
            // Si le nom n'est pas conforme
            new ErrorDialog("Nom de tâche invalide");
        }
    }
}
