package fr.groupe4.clientprojet.display.dialog.loaddialog.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.loaddialog.controller.EventLoadDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;

import java.awt.*;

public class LoadDialog extends DrawDialog {

    public LoadDialog(Communication comm, Window owner) {
        super(owner);
        setModal(true);
        setUndecorated(true);
        setSize(350, 80);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        comm.addPropertyChangeListener(new EventLoadDialog(this));
        comm.start();

        drawContent();

        setVisible(true);
        if (comm.isFinished()) {
            dispose();
        }
    }

    /**
     * Dessine le contenu
     */
    @Override
    protected void drawContent() {
        setBackground(Theme.FOND.getColor());
        add(new LoadCanvas());
    }
}
