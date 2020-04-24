package fr.groupe4.clientprojet.display.mainwindow.controller;

import fr.groupe4.clientprojet.display.dialog.exitdialog.view.ExitDialog;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;
import org.jetbrains.annotations.NotNull;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Listener de la fenêtre principale
 */
public class EventMainWindow extends WindowAdapter {
    /**
     * La frame dont viennent les appels
     */
    @NotNull
    private MainWindow source;

    /**
     * Constructeur
     *
     * @param source : la frame dont viennent les appels
     */
    public EventMainWindow(@NotNull MainWindow source) {
        this.source = source;
    }

    /**
     * Quand la fenêtre est fermée
     *
     * @param e : l'event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        new ExitDialog(source, "Êtes-vous sûr de vouloir quitter ?");
    }

    /**
     * Quand la fenêtre est ouverte
     *
     * @param e : l'event
     */
    @Override
    public void windowOpened(WindowEvent e) {
        source.getCenterPanel().redraw();
        source.getLeftPanel().redraw();
    }
}
