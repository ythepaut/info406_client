package fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.controller;

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
        String password = source.getPassword();
        // TODO : Modification du mot de passe
    }
}
