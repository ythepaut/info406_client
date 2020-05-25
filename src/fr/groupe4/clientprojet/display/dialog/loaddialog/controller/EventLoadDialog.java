package fr.groupe4.clientprojet.display.dialog.loaddialog.controller;

import fr.groupe4.clientprojet.communication.enums.CommunicationPropertyName;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EventLoadDialog implements PropertyChangeListener {
    private LoadDialog source;

    public EventLoadDialog(LoadDialog source) {
        this.source = source;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();

        if (CommunicationPropertyName.fromString(propertyName) == CommunicationPropertyName.COMMUNICATION_LOADING_FINISHED) {
            source.dispose();
        }
    }
}
