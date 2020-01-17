package fr.groupe4.clientprojet.exitdialog;

import fr.groupe4.clientprojet.mainwindow.EventMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventExitDialog extends WindowAdapter implements ActionListener {
    public final static String EXIT = "exit";
    public final static String CANCEL = "cancel";

    private JDialog source;

    public EventExitDialog(JDialog source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case EXIT:
                source.getOwner().dispose();
                break;

            case CANCEL:
                source.dispose();
                break;

            default:
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
    }
}
