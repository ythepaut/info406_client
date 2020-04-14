package fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller;

import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view.UsersGestionDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventExitGestionUsers implements ActionListener {

    //Attributs
    UsersGestionDialog source;

    public EventExitGestionUsers(UsersGestionDialog source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        source.dispose();
    }
}
