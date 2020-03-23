package fr.groupe4.clientprojet.display.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(trackBounds.x,
                trackBounds.y,
                trackBounds.width,
                trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(new Color(0, 0, 0, 25));
        g.fillRoundRect(thumbBounds.x,
                thumbBounds.y,
                thumbBounds.width,
                thumbBounds.height,
                thumbBounds.width *2,
                thumbBounds.width *2);
    }
}
