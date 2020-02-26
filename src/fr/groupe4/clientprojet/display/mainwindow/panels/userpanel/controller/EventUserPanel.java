package fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.controller;

import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.enums.UserChoice;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.view.UserPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Le listener du panel utilisateur
 */
public class EventUserPanel implements ActionListener {
    /**
     * Le panel utilisateur
     */
    private UserPanel source;

    /**
     * Le contructeur
     *
     * @param source : Le panel utilisateur
     */
    public EventUserPanel(UserPanel source) {
        this.source = source;
    }

    /**
     * Quand un bouton est clické
     *
     * @param e : l'event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (UserChoice.getEnum(e.getActionCommand())) {
            case PASSWORD:
                String password = source.getPassword();
                System.out.println(password);
                // TODO : Modification du mot de passe
                break;

            case MAIL:
                String mail = source.getEmail();
                System.out.println(mail);
                // TODO : Modification du mail
                break;

            default:
        }
    }
}
