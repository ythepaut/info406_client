package fr.groupe4.clientprojet.display.dialog.usersadddialog.controller;

import fr.groupe4.clientprojet.display.dialog.usersadddialog.view.UsersAddDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventExitGestionUsers implements ActionListener {

    //Attributs
    UsersAddDialog source;

    public EventExitGestionUsers(UsersAddDialog source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        source.dispose();
    }
}
