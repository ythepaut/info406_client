package fr.groupe4.clientprojet.display.dialog.errordialog.controller;

import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventErrorDialog extends WindowAdapter implements ActionListener {
    private ErrorDialog source;

    public EventErrorDialog(ErrorDialog source) {
        this.source = source;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        source.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        source.dispose();
    }
}
