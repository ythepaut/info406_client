package fr.groupe4.clientprojet.display.dialog.connectiondialog.controller;

import fr.groupe4.clientprojet.Main;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.enums.ConnectionChoice;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.view.ConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Listener de la connexion
 */
public class EventConnectionDialog extends WindowAdapter implements ActionListener {
    /**
     * Dialog de connexion
     */
    @NotNull
    private final ConnectionDialog source;

    /**
     * Constructeur
     *
     * @param source Dialog de connexion
     */
    public EventConnectionDialog(@NotNull ConnectionDialog source) {
        this.source = source;
    }

    /**
     * Quand la fenêtre est fermée
     *
     * @param e Event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        source.dispose();
        Main.exit();
    }

    /**
     * Quand un bouton est cliqué
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (ConnectionChoice.getEnum(e.getActionCommand())) {
            case OK:
                Communication comm = Communication.builder()
                        .connect(source.getUsername(), source.getPassword())
                        .build();

                new LoadDialog(comm, source);

                if (Communication.isConnected()) {
                    source.dispose();
                } else {
                    new ErrorDialog(comm.getMessage(), source);
                    source.resetPassword();
                }
                break;

            case CANCEL:
                source.dispose();
                Main.exit();
                break;

            default:
                break;
        }

    }
}
