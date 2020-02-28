package fr.groupe4.clientprojet.display.dialog.loaddialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.communication.enums.PropertyName;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
     * @param comm : L'instance de Communication qu'on observe
     */
    public EventLoadDialog(LoadDialog source, Communication comm) {
        this.source = source;
        comm.addPropertyChangeListener(this);
    }

    /**
     * Ferme la fenêtre quand le chargement est fini
     *
     * @param evt Event de changement de propriété
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (PropertyName.LOADDIALOG.getName().equals(propertyName)) {
            source.dispose();
        }
    }
}
