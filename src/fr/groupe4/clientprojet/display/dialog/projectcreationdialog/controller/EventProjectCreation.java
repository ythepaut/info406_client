package fr.groupe4.clientprojet.display.dialog.projectcreationdialog.controller;

import com.github.lgooddatepicker.components.DatePicker;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.project.enums.ProjectStatus;
import org.jetbrains.annotations.NotNull;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * Création de projet
 */
public class EventProjectCreation implements ActionListener {
    /**
     * Source
     */
    private final DrawDialog source;

    /**
     * Sélecteur de date
     */
    private final DatePicker datePicker;

    /**
     * Textfield du nom
     */
    private final JTextField nameTextField;

    /**
     * Zone de texte pour la description
     */
    private final JTextArea descriptionTextArea;

    /**
     * Constructeur
     *
     * @param source Source Swing
     * @param datePicker Sélecteur de date
     * @param nameTextField TextField du nom
     * @param descriptionTextArea TextArea de la description
     */
    public EventProjectCreation(@NotNull DrawDialog source,
                                @NotNull DatePicker datePicker,
                                @NotNull JTextField nameTextField,
                                @NotNull JTextArea descriptionTextArea) {
        this.source = source;
        this.datePicker = datePicker;
        this.nameTextField = nameTextField;
        this.descriptionTextArea = descriptionTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        LocalDate date = datePicker.getDate();
        String name = nameTextField.getText();
        String description = descriptionTextArea.getText();

        if (!name.isBlank() && name.length() >= Project.MIN_NAME_LENGTH && name.length() < Project.MAX_NAME_LENGTH) {
            Communication c = Communication.builder()
                    .createProject(name, description, date, ProjectStatus.ONGOING)
                    .build();

            new LoadDialog(c);

            switch (c.getHTTPCode()) {
                case HTTP_FORBIDDEN:
                    // Pas les permissions de créer un projet
                    new ErrorDialog("Vous n'avez pas les permissions nécessaires");
                    break;

                case HTTP_OK:
                    // Projet créé
                    new ErrorDialog("Projet créé", "SUCCESS", ErrorDialog.COLOR_OK);
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
