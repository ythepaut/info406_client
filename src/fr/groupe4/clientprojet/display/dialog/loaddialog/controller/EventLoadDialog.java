package fr.groupe4.clientprojet.display.dialog.loaddialog.controller;

import fr.groupe4.clientprojet.communication.enums.CommunicationPropertyName;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Le listener du LoadDialog
 */
public class EventLoadDialog implements PropertyChangeListener {
    /**
     * Le LoadDialog
     */
    @NotNull
    private final LoadDialog source;

    /**
     * Le constructeur
     *
     * @param source : Le loadDialog
     */
    public EventLoadDialog(@NotNull LoadDialog source) {
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

        if (CommunicationPropertyName.fromString(propertyName)
                == CommunicationPropertyName.COMMUNICATION_LOADING_FINISHED) {
            source.dispose();
        }
    }
}
