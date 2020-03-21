package fr.groupe4.clientprojet.display.dialog.firstrundialog.controller;

import fr.groupe4.clientprojet.display.dialog.firstrundialog.view.FirstRunDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Le listener du dialog de premier de lancement
 */
public class EventFirstRunDialog extends WindowAdapter implements ActionListener {
    /**
     * Le dialog de premier lancement
     */
    private FirstRunDialog source;
    /**
     * Variables statiques pour les 2 boutons du dialog
     */
    public final static String OK = "ok", CANCEL = "cancel";


    /**
     * Le constructeur
     *
     * @param source Le dialog de premier lancement
     */
    public EventFirstRunDialog(FirstRunDialog source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case OK:
                Parameters.setServerUrl(source.getUrl());
                Parameters.setFirstRun(false);
                source.dispose();
                break;

            case CANCEL:
                source.dispose();
                break;

            default:
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        source.dispose();
    }
}