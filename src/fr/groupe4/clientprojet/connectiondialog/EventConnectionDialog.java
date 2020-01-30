package fr.groupe4.clientprojet.connectiondialog;

import fr.groupe4.clientprojet.connectiondialog.enums.ConnectionChoice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventConnectionDialog implements ActionListener {
    private JDialog source;
    private JTextField identifiant;
    private JPasswordField password;

    public EventConnectionDialog(JDialog source) {
        this.source = source;
    }

    public void setIdentifiant(JTextField identifiant) {
        this.identifiant = identifiant;
    }

    public void setPassword(JPasswordField password) {
        this.password = password;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (ConnectionChoice.getEnum(e.getActionCommand())) {
            case OK:
                // TODO: Appel à la méthode de connexion
                identifiant.getText();
                password.getPassword();
                break;

            case CANCEL:
                source.dispose();
                break;
        }
    }
}
