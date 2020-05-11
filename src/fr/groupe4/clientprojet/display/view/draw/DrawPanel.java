package fr.groupe4.clientprojet.display.view.draw;

import javax.swing.JPanel;

/**
 * Classe abstraite héritant de JPanel qui rajoute 2 méthodes
 */
public abstract class DrawPanel extends JPanel {

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
