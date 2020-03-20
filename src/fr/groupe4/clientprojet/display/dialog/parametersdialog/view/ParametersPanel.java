package fr.groupe4.clientprojet.display.dialog.parametersdialog.view;

import javax.swing.*;

/**
 * Panel pour les paramètres
 */
public class ParametersPanel extends JPanel {
    /**
     * Titre du panel qui est affiché sur le noeud de l'arbre
     */
    private String title;

    /**
     * Le constructeur
     *
     * @param title Titre du panel
     */
    public ParametersPanel(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
