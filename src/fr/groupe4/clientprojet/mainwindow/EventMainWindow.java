package fr.groupe4.clientprojet.mainwindow;

import fr.groupe4.clientprojet.connectiondialog.ConnectionDialog;
import fr.groupe4.clientprojet.exitdialog.ExitDialog;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Listener de la fenêtre principale
 */
public class EventMainWindow extends WindowAdapter {
    /**
     * La frame dont viennent les appels
     */
    private JFrame source;

    /**
     * Constructeur
     *
     * @param source : la frame dont viennent les appels
     */
    public EventMainWindow(JFrame source) {
        this.source = source;
    }

    /**
     * Quand la fenêtre est ouverte
     *
     * @param e : l'event
     */
    @Override
    public void windowOpened(WindowEvent e) {
        //if (!Communication.isConnected()) { TODO: Enlever le commentaire une fois que la méthode existera
            new ConnectionDialog(source);
        //}
    }

    /**
     * Quand la fenêtre est fermée
     *
     * @param e : l'event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        new ExitDialog(source);
    }

}
