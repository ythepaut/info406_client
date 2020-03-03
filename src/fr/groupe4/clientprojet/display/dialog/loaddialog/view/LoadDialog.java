package fr.groupe4.clientprojet.display.dialog.loaddialog.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.loaddialog.controller.EventLoadDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog de chargement
 * Permet de notifier l'utilisateur d'un chargement
 */
public class LoadDialog extends JDialog {
    /**
     * Le constructeur
     *
     * @param owner : le dialog auquel appartient le loaddialog
     * @param comm : l'instance de communication
     */
    public LoadDialog(JDialog owner, Communication comm) {
        super(owner, true);
        addPropertyChangeListener(new EventLoadDialog(this, comm));
        comm.start();
        setUndecorated(true);
        setSize(350, 80);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        add(new LoadCanvas());

        if (comm.isFinished()) {
            dispose();
        }
        else {
            setVisible(true);
        }
    }
}
