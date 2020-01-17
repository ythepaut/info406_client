package fr.groupe4.clientprojet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EventMainWindow extends WindowAdapter implements ActionListener {
    public final static String EXIT = "exit";
    public final static String CONNECTION = "connection";
    public final static String ADDTASK = "addTask";
    public final static String DELETETASK = "deleteTask";

    private Frame source;

    EventMainWindow(Frame pOwner) {
        source = pOwner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case EXIT:
                new ExitDialog(source);
                break;

            case CONNECTION:
                // TODO: Créer la fenêtre de connexion
                break;

            case ADDTASK:
                // TODO: Créer la fenêtre d'ajout de tâches
                break;

            case DELETETASK:
                // TODO: Créer la fenêtre de suppression de tâches
                break;


            default:

        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        new ExitDialog(e.getWindow());
    }
}
