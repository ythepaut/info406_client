package fr.groupe4.clientprojet.display.dialog.loaddialog.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.loaddialog.controller.EventLoadDialog;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog de chargement <br>
 * Permet de notifier l'utilisateur d'un chargement
 */
public class LoadDialog extends JDialog {
    /**
     * Constructeur
     *
     * @param comm Instance de communication
     */
    public LoadDialog(@NotNull Communication comm) {
        setModal(true);
        comm.addPropertyChangeListener(new EventLoadDialog(this));
        comm.start();
        setUndecorated(true);
        setSize(350, 80);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);

        add(new LoadCanvas());

        if (comm.isFinished()) {
            dispose();
        } else {
            setVisible(true);
        }
    }
}
