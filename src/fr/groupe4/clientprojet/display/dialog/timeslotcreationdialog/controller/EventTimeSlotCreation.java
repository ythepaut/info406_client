package fr.groupe4.clientprojet.display.dialog.timeslotcreationdialog.controller;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.task.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalDateTime;

public class EventTimeSlotCreation implements ActionListener {
    private DrawDialog parent;
    private DatePicker datePicker;
    private TimePicker timePickerFrom;
    private TimePicker timePickerTo;
    private Task task;

    public EventTimeSlotCreation(DrawDialog parent, DatePicker datePicker, TimePicker timePickerFrom, TimePicker timePickerTo, Task task) {
        this.parent = parent;
        this.datePicker = datePicker;
        this.timePickerFrom = timePickerFrom;
        this.timePickerTo = timePickerTo;
        this.task = task;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LocalDateTime from = LocalDateTime.of(datePicker.getDate(), timePickerFrom.getTime());
        LocalDateTime to = LocalDateTime.of(datePicker.getDate(), timePickerTo.getTime());

        long roomId = 69;

        if (from.isBefore(to)) {
            Communication c = Communication.builder()
                    .addTimeSlot(from, to, task.getId(), roomId)
                    .build();

            new LoadDialog(c);

            switch (c.getHTTPCode()) {
                case HTTP_FORBIDDEN:
                    // Pas les permissions de créer un projet
                    new ErrorDialog("Vous n'avez pas les permissions nécessaires");
                    break;

                case HTTP_OK:
                    // Projet créé
                    new ErrorDialog("Créneau ajouté", "SUCCESS", ErrorDialog.COLOR_OK);
                    parent.dispose();
                    break;

                case HTTP_BAD_REQUEST:
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
