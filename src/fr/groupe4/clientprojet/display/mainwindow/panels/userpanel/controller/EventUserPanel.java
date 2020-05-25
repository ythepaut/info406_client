package fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.controller;

import fr.groupe4.clientprojet.communication.enums.CommunicationPropertyName;
import fr.groupe4.clientprojet.display.dialog.parametersdialog.view.ParametersDialog;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.enums.UserChoice;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.view.UserPanel;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static fr.groupe4.clientprojet.communication.enums.CommunicationPropertyName.COMMUNICATION_NEWS_CHANGED;

/**
 * Le listener du panel utilisateur
 */
public class EventUserPanel implements ActionListener, PropertyChangeListener {
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

            case SETTINGS:
                new ParametersDialog(MainWindow.getInstance());
                break;

            default:
        }
    }

    /**
     * Quand le fil d'actualité change
     *
     * @param e : L'event
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();

        if (CommunicationPropertyName.fromString(propertyName) == COMMUNICATION_NEWS_CHANGED) {
            source.redraw();
        }
    }
}
