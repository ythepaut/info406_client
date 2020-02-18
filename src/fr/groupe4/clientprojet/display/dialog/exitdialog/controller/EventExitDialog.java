package fr.groupe4.clientprojet.display.dialog.exitdialog.controller;

import fr.groupe4.clientprojet.display.dialog.exitdialog.enums.ExitChoice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Le listener du dialog de confirmation de sortie
 */
public class EventExitDialog extends WindowAdapter implements ActionListener {
    /**
     * Le dialog en question
     */
    private JDialog source;

    /**
     * Le constructeur
     *
     * @param source : le dialog
     */
    public EventExitDialog(JDialog source) {
        this.source = source;
    }

    /**
     * Quand un bouton est cliqué
     *
     * @param e : l'event
     */
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

    /**
     * Quand on ferme la fenêtre
     *
     * @param e : l'event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
    }
}
