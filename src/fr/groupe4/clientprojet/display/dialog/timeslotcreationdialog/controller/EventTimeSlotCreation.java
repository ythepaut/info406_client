package fr.groupe4.clientprojet.display.dialog.timeslotcreationdialog.controller;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.task.Task;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

/**
 * Event d'ajout de créneau
 */
public class EventTimeSlotCreation implements ActionListener {
    /**
     * Parent
     */
    @NotNull
    private final DrawDialog parent;

    /**
     * Sélecteur de date
     */
    @NotNull
    private final DatePicker datePicker;

    /**
     * Sélecteur d'heure 1
     */
    @NotNull
    private final TimePicker timePickerFrom;

    /**
     * Sélecteur d'heure 2
     */
    @NotNull
    private final TimePicker timePickerTo;

    /**
     * Tâche parente
     */
    @NotNull
    private final Task task;

    /**
     * Constructeur
     *
     * @param parent Parent
     * @param datePicker Sélecteur de date
     * @param timePickerFrom Sélecteur d'heure 1
     * @param timePickerTo Sélecteur d'heure 2
     * @param task Tâche actuelle
     */
    public EventTimeSlotCreation(@NotNull DrawDialog parent,
                                 @NotNull DatePicker datePicker,
                                 @NotNull TimePicker timePickerFrom,
                                 @NotNull TimePicker timePickerTo,
                                 @NotNull Task task) {
        this.parent = parent;
        this.datePicker = datePicker;
        this.timePickerFrom = timePickerFrom;
        this.timePickerTo = timePickerTo;
        this.task = task;
    }

    /**
     * Clic sur le bouton
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        LocalDateTime from = LocalDateTime.of(datePicker.getDate(), timePickerFrom.getTime());
        LocalDateTime to = LocalDateTime.of(datePicker.getDate(), timePickerTo.getTime());

        // TODO
        long roomId = 69;

        if (from.isBefore(to)) {
            Communication c = Communication.builder()
                    .addTimeSlot(from, to, task.getId(), roomId)
                    .build();

            new LoadDialog(c);

            switch (c.getHTTPCode()) {
                case HTTP_FORBIDDEN:
                    // Pas les permissions de créer un projet
                    Logger.warning("Pas les permissions", c);
                    new ErrorDialog("Vous n'avez pas les permissions nécessaires");
                    break;

                case HTTP_OK:
                    // Projet créé
                    Logger.success("Créneau ajouté");
                    new ErrorDialog("Créneau ajouté", "SUCCESS", ErrorDialog.COLOR_OK);
                    parent.dispose();
                    break;

                case HTTP_BAD_REQUEST:
                    Logger.warning("Créneau indisponible");
                    new ErrorDialog("Ce créneau est indisponible");
                    break;

                default:
                    Logger.error("Code invalide :", c);
                    break;
            }
        }
        else {
            new ErrorDialog("Temps invalides : date de début après date de fin");
        }
    }
}
