package fr.groupe4.clientprojet.mainwindow;

import fr.groupe4.clientprojet.exitdialog.ExitDialog;
import fr.groupe4.clientprojet.mainwindow.enums.MenubarAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventMainWindow extends WindowAdapter implements ActionListener {

    private JFrame source;

    public EventMainWindow(JFrame source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (MenubarAction.getEnum(e.getActionCommand())) {
            case CONNECTION:
                break;

            case SETTING:
                break;

            case EXIT:
                new ExitDialog(source);
                break;

            case ADDTASK:
                break;

            case DELETETASK:
                break;

            default:
                break;
        }

    }

    @Override
    public void windowClosing(WindowEvent e) {
        new ExitDialog(source);
    }

}
