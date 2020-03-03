package fr.groupe4.clientprojet.display.dialog.connectiondialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.view.ConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyEventConnectionDialog extends KeyAdapter {
    private ConnectionDialog source;

    public KeyEventConnectionDialog(ConnectionDialog source) {
        this.source = source;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("ok");
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Communication comm = Communication.builder()
                    .connect(source.getUsername(), source.getPassword())
                    .build();

            new LoadDialog(source, comm);

            if (Communication.isConnected()) {
                source.dispose();
            } else {
                new ErrorDialog(source, comm.getMessage()); // TODO : adapter le message en fonction de l'erreur
            }
        }
    }
}
