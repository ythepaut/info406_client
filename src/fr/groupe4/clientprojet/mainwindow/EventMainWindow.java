package fr.groupe4.clientprojet.mainwindow;

import fr.groupe4.clientprojet.exitdialog.ExitDialog;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventMainWindow extends WindowAdapter implements ActionListener {
    public final static String CONNECTION = "connection";
    public final static String SETTING = "setting";
    public final static String EXIT = "exit";
    public final static String ADDTASK = "addTask";
    public final static String DELETETASK = "deleteTask";
    private JFrame source;

    public EventMainWindow(JFrame source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
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
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        new ExitDialog(source);
    }
}
