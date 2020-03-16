package fr.groupe4.clientprojet.display.dialog.errordialog.controller;

import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyEventErrorDialog extends KeyAdapter {
    private ErrorDialog source;

    public KeyEventErrorDialog(ErrorDialog source) {
        this.source = source;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            source.dispose();
        }
    }
}
