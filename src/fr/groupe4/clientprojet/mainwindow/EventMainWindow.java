package fr.groupe4.clientprojet.mainwindow;

import fr.groupe4.clientprojet.exitdialog.ExitDialog;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventMainWindow extends WindowAdapter {

    private JFrame source;

    public EventMainWindow(JFrame source) {
        this.source = source;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        new ExitDialog(source);
    }

}
