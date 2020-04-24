package fr.groupe4.clientprojet.display.dialog.errordialog.controller;

import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Gestion des touches sur le dialog d'erreur
 */
public class KeyEventErrorDialog extends KeyAdapter {
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
    public KeyEventErrorDialog(@NotNull ErrorDialog source) {
        this.source = source;
    }

    /**
     * Touche pressée
     *
     * @param e Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            source.dispose();
        }
    }
}
