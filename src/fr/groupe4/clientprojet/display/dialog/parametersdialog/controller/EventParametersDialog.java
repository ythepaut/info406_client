package fr.groupe4.clientprojet.display.dialog.parametersdialog.controller;

import fr.groupe4.clientprojet.display.dialog.parametersdialog.view.ParametersDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Le listener du dialog des paramètres
 */
public class EventParametersDialog extends WindowAdapter implements ActionListener {
    /**
     * Le dialog des paramètres
     */
    private ParametersDialog source;

    /**
     * Le constructeur
     *
     * @param source Le dialog des paramètres
     */
    public EventParametersDialog(ParametersDialog source) {
        this.source = source;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        source.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}