package fr.groupe4.clientprojet.display.dialog.controller;

import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

/**
 * Event pour quitter un dialog
 */
public class GenericExitEvent extends WindowAdapter implements ActionListener {
    /**
     * Source parente
     */
    @NotNull
    private final DrawDialog parent;

    /**
     * Constructeur
     *
     * @param parent Parent
     */
    public GenericExitEvent(@NotNull DrawDialog parent) {
        this.parent = parent;
    }

    /**
     * En cas d'action effectuée (ex : bouton pressé)
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        parent.dispose();
    }

    /**
     * Dialog fermé
     *
     * @param e Event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        parent.dispose();
    }
}
