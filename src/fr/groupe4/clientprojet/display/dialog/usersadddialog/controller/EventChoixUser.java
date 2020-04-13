package fr.groupe4.clientprojet.display.dialog.usersadddialog.controller;

import fr.groupe4.clientprojet.display.dialog.usersadddialog.view.UsersAddDialog;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class EventChoixUser implements ItemListener {

    UsersAddDialog source;
    HumanResource usercourant;
    ArrayList<Boolean> userchoisis;

    public EventChoixUser(UsersAddDialog source, HumanResource usercourant, ArrayList<Boolean> userchoisis) {
        this.source = source;
        this.usercourant = usercourant;
        this.userchoisis = userchoisis;
    }


    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        itemEvent.getItemSelectable();
        if (itemEvent.getStateChange () == ItemEvent.SELECTED){
            System.out.println(usercourant.getFirstname() + " " +
                    usercourant.getLastname() + " selectionné !");
        } else {
            System.out.println(usercourant.getFirstname() + " " +
                    usercourant.getLastname() + " déselectionné !");
        }
    }
}
