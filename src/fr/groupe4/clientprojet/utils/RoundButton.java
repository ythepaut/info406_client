package fr.groupe4.clientprojet.utils;

import javax.swing.*;
import java.awt.*;

public class RoundButton extends JButton {
    private boolean selected;

    public RoundButton(String text) {
        super(text);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        selected = false;
    }

    public void setSelected(boolean b) {
        this.selected = b;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int taille = Math.min(getWidth(), getHeight());
        if (selected) {
            g2.setColor(new Color(84, 180, 255));
            g2.fillOval(taille/3, 1, taille-3, taille-3);
        }
        super.paintComponent(g);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.6f));
        g2.drawOval(taille/3, 1, taille-3, taille-3);
        g2.dispose();
    }
}
