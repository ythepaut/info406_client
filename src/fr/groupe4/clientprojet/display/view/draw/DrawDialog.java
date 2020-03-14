package fr.groupe4.clientprojet.display.view.draw;

import javax.swing.*;

/**
 * Classe abstraite héritant de JDialog qui rajoute 2 méthodes
 */
public abstract class DrawDialog extends JDialog {

    /**
     * Dessine le contenu
     */
    protected abstract void drawContent();

    /**
     * Redessine le panel
     */
    public final void redraw() {
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }
}
