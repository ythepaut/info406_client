package fr.groupe4.clientprojet.display.dialog.taskcreationdialog.controller;

import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view.TaskCreationDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventExitCreationDialog implements ActionListener {
    private TaskCreationDialog parent;

    public EventExitCreationDialog(TaskCreationDialog parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        parent.dispose();
    }
}
