package fr.groupe4.clientprojet.display.dialog.errordialog.controller;

import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Event des dialog d'erreur
 */
public class EventErrorDialog extends WindowAdapter implements ActionListener {
    /**
     * Source
     */
    @NotNull
    private final ErrorDialog source;

    /**
     * Constructeur
     *
     * @param source Source
     */
    public EventErrorDialog(@NotNull ErrorDialog source) {
        this.source = source;
    }

    /**
     * En cas de fermeture de fenêtre
     *
     * @param e Event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        source.dispose();
    }

    /**
     * En cas d'action effectuée (ex : bouton pressé)
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        source.dispose();
    }
}
