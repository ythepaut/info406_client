package fr.groupe4.clientprojet.display.dialog.usersadddialog.controller;

import fr.groupe4.clientprojet.display.dialog.usersadddialog.view.UsersAddDialog;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class EventChoixUser implements ItemListener {

    /**
     * Attributs necessaire au controller
     */
    private UsersAddDialog source;
    private HumanResource usercourant;
    private boolean[] userchoisis;
    private int idarraylist;

    /**
     * Constructeur
     * @param source
     * @param usercourant
     * @param userchoisis
     */
    public EventChoixUser(UsersAddDialog source, HumanResource usercourant, boolean[] userchoisis, int idarraylist) {
        this.source = source;
        this.usercourant = usercourant;
        this.userchoisis = userchoisis;
        this.idarraylist = idarraylist;
    }


    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

        if (itemEvent.getStateChange () == ItemEvent.SELECTED){
            System.out.println(usercourant.getFirstname() + " " +
                    usercourant.getLastname() + " selectionné !");
            userchoisis[idarraylist] = true;
        } else {
            System.out.println(usercourant.getFirstname() + " " +
                    usercourant.getLastname() + " déselectionné !");
            userchoisis[idarraylist] = false;
        }
    }
}
