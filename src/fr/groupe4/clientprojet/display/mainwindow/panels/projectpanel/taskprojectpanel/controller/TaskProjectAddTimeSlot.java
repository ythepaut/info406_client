package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.taskprojectpanel.controller;

import fr.groupe4.clientprojet.display.dialog.timeslotcreationdialog.view.TimeSlotCreationDialog;
import fr.groupe4.clientprojet.model.task.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskProjectAddTimeSlot implements ActionListener {
    private final Task task;

    public TaskProjectAddTimeSlot(Task task) {
        this.task = task;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new TimeSlotCreationDialog(task);
    }
}
