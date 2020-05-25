package fr.groupe4.clientprojet.display.view;

import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * ScrollBar
 */
public class ScrollBarUI extends BasicScrollBarUI {
    /**
     * Affichage
     *
     * @param g           Graphics
     * @param c           Component
     * @param trackBounds Limites
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(Theme.FOND.getColor(Parameters.getThemeName()));
        g.fillRect(trackBounds.x,
                trackBounds.y,
                trackBounds.width,
                trackBounds.height);
    }

    /**
     * Affichage
     *
     * @param g           Graphics
     * @param c           Component
     * @param thumbBounds Limites
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        int arc = (Math.min(thumbBounds.width, thumbBounds.height)) * 2;
        g.setColor(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        g.fillRoundRect(thumbBounds.x,
                thumbBounds.y,
                thumbBounds.width,
                thumbBounds.height,
                arc,
                arc);
    }
}
