package fr.groupe4.clientprojet.display.dialog.loaddialog.view;

import fr.groupe4.clientprojet.communication.Communication;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Dialog de chargement
 * Permet de notifier l'utilisateur d'un chargement
 */
public class LoadDialog extends JDialog implements PropertyChangeListener {
    /**
     * Le constructeur
     *
     * @param owner : le dialog auquel appartient le loaddialog
     * @param comm : l'instance de communication
     */
    public LoadDialog(JDialog owner, Communication comm) {
        super(owner, true);
        comm.addPropertyChangeListener(this);
        setUndecorated(true);
        setSize(350, 80);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        add(new LoadCanvas());

        setVisible(true);
    }

    /**
     * Ferme la fenêtre quand le chargement est fini
     *
     * @param evt Event de changement de propriété
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if ("loadingFinished".equals(propertyName)) {
            dispose();
        }
    }
}
