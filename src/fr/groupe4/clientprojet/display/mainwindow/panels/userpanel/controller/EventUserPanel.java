package fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.controller;

import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.enums.UserChoice;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.view.UserPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventUserPanel implements ActionListener {
    private UserPanel source;

    public EventUserPanel(UserPanel source) {
        this.source = source;
    }

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
