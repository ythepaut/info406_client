package fr.groupe4.clientprojet.exitdialog;

import fr.groupe4.clientprojet.exitdialog.enums.ExitChoice;
import fr.groupe4.clientprojet.mainwindow.EventMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventExitDialog extends WindowAdapter implements ActionListener {

    private JDialog source;

    public EventExitDialog(JDialog source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (ExitChoice.getEnum(e.getActionCommand())) {
            case EXIT:
                source.getOwner().dispose();
                break;
            case CANCEL:
                source.dispose();
                break;
            default:
                break;
        }

    }

    @Override
    public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
    }
}
