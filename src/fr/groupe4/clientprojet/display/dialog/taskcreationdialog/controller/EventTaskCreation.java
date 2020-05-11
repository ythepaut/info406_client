package fr.groupe4.clientprojet.display.dialog.taskcreationdialog.controller;

import com.github.lgooddatepicker.components.DatePicker;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.task.Task;
import fr.groupe4.clientprojet.model.task.enums.TaskStatus;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * Création de tâche
 */
public class EventTaskCreation implements ActionListener {
    /**
     * Dialog parent
     */
    @NotNull
    private final DrawDialog parent;

    /**
     * Sélecteur de date
     */
    @NotNull
    private final DatePicker datePicker;

    /**
     * Text field pour le nom
     */
    @NotNull
    private final JTextField nameTextField;

    /**
     * TextArea de la description
     */
    @NotNull
    private final JTextArea descriptionTextArea;

    /**
     * Projet associé
     */
    @NotNull
    private final Project project;

    /**
     * Constructeur
     *
     * @param parent              Parent
     * @param datePicker          Sélecteur de date
     * @param nameTextField       TextField de nom
     * @param descriptionTextArea TextArea de description
     * @param project             Projet
     */
    public EventTaskCreation(@NotNull DrawDialog parent,
                             @NotNull DatePicker datePicker,
                             @NotNull JTextField nameTextField,
                             @NotNull JTextArea descriptionTextArea,
                             @NotNull Project project) {
        this.parent = parent;
        this.datePicker = datePicker;
        this.nameTextField = nameTextField;
        this.descriptionTextArea = descriptionTextArea;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LocalDate date = datePicker.getDate();
        String nom = nameTextField.getText();
        String description = descriptionTextArea.getText();

        if (!nom.isBlank() && nom.length() >= Task.MIN_NAME_LENGTH && nom.length() < Task.MAX_NAME_LENGTH) {
            Communication c = Communication.builder()
                    .createTask(nom, description, TaskStatus.ONGOING, date, project.getId())
                    .build();

            new LoadDialog(c);

            switch (c.getHTTPCode()) {
                case HTTP_FORBIDDEN:
                    // Pas les permissions de créer un projet
                    Logger.warning("Pas les permissions");
                    new ErrorDialog("Vous n'avez pas les permissions nécessaires", parent);
                    break;

                case HTTP_OK:
                    // Projet créé
                    Logger.success("Tâche créée");
                    new ErrorDialog("Tâche créée", "SUCCESS", ErrorDialog.COLOR_OK, parent);
                    parent.dispose();
                    break;

                case HTTP_BAD_REQUEST:
                    Logger.warning("Tâche avec ce nom");
                    new ErrorDialog("Une tâche avec ce nom existe déjà", parent);
                    break;

                default:
                    Logger.error("Code invalide :", c);
                    break;
            }
        } else {
            // Si le nom n'est pas conforme
            Logger.warning("Nom de tâche invalide");
            new ErrorDialog("Nom de tâche invalide", parent);
        }
    }
}
