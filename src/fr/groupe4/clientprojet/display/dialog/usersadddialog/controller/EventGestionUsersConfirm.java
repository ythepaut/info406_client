package fr.groupe4.clientprojet.display.dialog.usersadddialog.controller;

import fr.groupe4.clientprojet.display.dialog.usersadddialog.view.UsersAddDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventGestionUsersConfirm implements ActionListener {

    //Attributs
    UsersAddDialog source;
    Boolean[] userschoisis;

    public EventGestionUsersConfirm(UsersAddDialog source, Boolean[] userchoisis) {
        this.source = source;
        this.userschoisis = userchoisis;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //Ajoutez les utilisateurs du tableau, au serveur :)
    }
}
