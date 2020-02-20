package fr.groupe4.clientprojet.display.mainwindow.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.view.ConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.exitdialog.view.ExitDialog;

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
     * Quand la fenêtre est fermée
     *
     * @param e : l'event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        new ExitDialog(source);
    }

}
