package fr.groupe4.clientprojet.display.dialog.loaddialog.view;

import fr.groupe4.clientprojet.communication.Communication;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Dialog de chargement
 * Permet de notifier l'utilisateur d'un chargement
 */
public class LoadDialog extends JDialog implements Observer {
    private Communication comm;

    /**
     * Le constructeur
     *
     * @param owner : le dialog auquel appartient le loaddialog
     * @param comm : l'instance de communication (pour le pattern observable, observer)
     */
    public LoadDialog(JDialog owner, Communication comm) {
        super(owner, true);
        this.comm = comm;
        comm.addObserver(this);
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
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        this.dispose();
    }
}
