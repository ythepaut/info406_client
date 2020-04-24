package fr.groupe4.clientprojet.display.dialog.connectiondialog.controller;

import fr.groupe4.clientprojet.Main;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.view.ConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Event clavier pour le dialog de connexion
 */
public class KeyEventConnectionDialog extends KeyAdapter {
    /**
     * Dialog source
     */
    private final ConnectionDialog source;

    /**
     * Constructeur
     *
     * @param source Dialog parent
     */
    public KeyEventConnectionDialog(ConnectionDialog source) {
        this.source = source;
    }

    /**
     * Touche enfoncée
     *
     * @param e Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Communication comm = Communication.builder()
                        .connect(source.getUsername(), source.getPassword())
                        .build();

                new LoadDialog(comm);

                if (Communication.isConnected()) {
                    source.dispose();
                } else {
                    new ErrorDialog(comm.getMessage());
                    source.resetPassword();
                }
                break;

            case KeyEvent.VK_ESCAPE:
                source.getOwner().dispose();
                Main.exit();
                break;

            default:
                break;
        }
    }
}
