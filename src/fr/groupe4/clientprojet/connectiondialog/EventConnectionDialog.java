package fr.groupe4.clientprojet.connectiondialog;

import fr.groupe4.clientprojet.connectiondialog.enums.ConnectionChoice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Le listener de la connexion
 */
public class EventConnectionDialog extends WindowAdapter implements ActionListener {
    /**
     * Le dialog de connexion
     */
    private ConnectionDialog source;

    /**
     * Le constructeur
     *
     * @param source : le dialog de connexion
     */
    public EventConnectionDialog(ConnectionDialog source) {
        this.source = source;
    }

    /**
     * Quand la fenêtre est fermée
     *
     * @param e : l'event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        source.getOwner().dispose();
    }

    /**
     * Quand un bouton est clické
     *
     * @param e : l'event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (ConnectionChoice.getEnum(e.getActionCommand())) {
            case OK:
                //Communication.connect(source.getUsername(), source.getPassword()); TODO: Enlever le commentaire une fois que la méthode existera
                break;

            case CANCEL:
                source.getOwner().dispose();
                break;
        }

    }
}
