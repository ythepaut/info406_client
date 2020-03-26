package fr.groupe4.clientprojet.display.dialog.loaddialog.controller;

import fr.groupe4.clientprojet.communication.enums.CommunicationPropertyName;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static fr.groupe4.clientprojet.communication.enums.CommunicationPropertyName.*;

/**
 * Le listener du LoadDialog
 */
public class EventLoadDialog implements PropertyChangeListener {
    /**
     * Le LoadDialog
     */
    private LoadDialog source;

    /**
     * Le constructeur
     *
     * @param source : Le loadDialog
     */
    public EventLoadDialog(LoadDialog source) {
        this.source = source;
    }

    /**
     * Ferme la fenêtre quand le chargement est fini
     *
     * @param evt Event de changement de propriété
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (CommunicationPropertyName.fromString(propertyName) == COMMUNICATION_LOADING_FINISHED) {
            source.dispose();
        }
    }
}
