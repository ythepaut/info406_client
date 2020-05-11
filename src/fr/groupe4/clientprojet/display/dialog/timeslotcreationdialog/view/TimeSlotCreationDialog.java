package fr.groupe4.clientprojet.display.dialog.timeslotcreationdialog.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.timeslotcreationdialog.controller.EventTimeSlotCreation;
import fr.groupe4.clientprojet.display.dialog.timeslotcreationdialog.controller.TimeSlotCreationPolicy;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.taskprojectpanel.view.TaskProjectPanel;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.task.Task;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.DayOfWeek;

public class TimeSlotCreationDialog extends DrawDialog {
    /**
     * Largeur et hauteur de la fenêtre
     */
    private static final int WIDTH = 300, HEIGHT = 550;

    /**
     * Tâche en cours
     */
    @NotNull
    private final Task task;

    public TimeSlotCreationDialog(@NotNull Task task, Window owner) {
        super(owner);
        this.task = task;

        setTitle("Fenêtre d'ajout' de créneau");
        setModal(true);

        drawContent();
        setVisible(true);
    }

    @Override
    protected void drawContent() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        GridBagConstraints c = new GridBagConstraints();

         // Déclaration du layout
        setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5,0,5,0);

        add(new JLabel("Ajout d'un créneau"), c);

        c.gridy++;
        add(new JLabel("Date :"), c);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(dateSettings);
        c.gridy++;
        add(datePicker, c);

        c.gridy++;
        add(new JLabel("Heure de début :"), c);

        TimePickerSettings timeSettingsFrom = new TimePickerSettings();
        timeSettingsFrom.use24HourClockFormat();
        timeSettingsFrom.generatePotentialMenuTimes(
                TimePickerSettings.TimeIncrement.TenMinutes,
                null,
                null);

        TimePicker timePickerFrom = new TimePicker(timeSettingsFrom);
        c.gridy++;
        add(timePickerFrom, c);

        c.gridy++;
        add(new JLabel("Heure de fin :"), c);

        TimePickerSettings timeSettingsTo = new TimePickerSettings();
        timeSettingsTo.use24HourClockFormat();
        timeSettingsTo.generatePotentialMenuTimes(
                TimePickerSettings.TimeIncrement.TenMinutes,
                null,
                null);

        TimePicker timePickerTo = new TimePicker(timeSettingsTo);
        c.gridy++;
        add(timePickerTo, c);

        timeSettingsFrom.setVetoPolicy(new TimeSlotCreationPolicy(TimeSlotCreationPolicy.BEFORE, timePickerTo));
        timeSettingsTo.setVetoPolicy(new TimeSlotCreationPolicy(TimeSlotCreationPolicy.AFTER, timePickerFrom));

        JButton addTimeSlotButton = new JButton("Ajouter créneau");
        addTimeSlotButton.addActionListener(new EventTimeSlotCreation(
                this,
                datePicker,
                timePickerFrom,
                timePickerTo,
                task));

        c.gridwidth = 1;
        c.gridy++;
        add(addTimeSlotButton, c);


        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new GenericExitEvent(this));
        c.gridx++;
        add(cancelButton, c);
    }
}
