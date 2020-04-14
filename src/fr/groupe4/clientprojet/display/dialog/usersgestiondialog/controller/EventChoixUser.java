package fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller;

import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view.UsersGestionDialog;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EventChoixUser implements ItemListener {

    /**
     * Attributs necessaire au controller
     */
    private UsersGestionDialog source;
    private HumanResource usercourant;
    private boolean[] userchoisis;
    private int idarraylist;

    /**
     * Constructeur
     * @param source
     * @param usercourant
     * @param userchoisis
     */
    public EventChoixUser(UsersGestionDialog source, HumanResource usercourant, boolean[] userchoisis, int idarraylist) {
        this.source = source;
        this.usercourant = usercourant;
        this.userchoisis = userchoisis;
        this.idarraylist = idarraylist;
    }


    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

        if (itemEvent.getStateChange () == ItemEvent.SELECTED){
            userchoisis[idarraylist] = true;
        } else {
            userchoisis[idarraylist] = false;
        }
    }
}
