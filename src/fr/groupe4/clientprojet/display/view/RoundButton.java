package fr.groupe4.clientprojet.display.view;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

/**
 * Permet de créer des boutons rond avec ou sans image <br>
 * Sans image -&gt; un contour rond noir autour du bouton <br>
 * Avec image -&gt; l'image centrée sur le boutons, sans texte
 */
public class RoundButton extends JButton {
    /**
     * Si le bouton est sélectionné ou pas
     */
    private boolean selected;

    /**
     * L'image du bouton (pour bouton avec image)
     */
    @Nullable
    private Image image;

    /**
     * Constructeur du bouton sans image
     *
     * @param text Texte du bouton
     */
    public RoundButton(@NotNull String text) {
        super(text);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        selected = false;
    }

    /**
     * Constructeur du bouton avec image
     *
     * @param file Image du bouton
     */
    public RoundButton(File file) {
        this(" ");
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            Logger.warning(e);
        }
    }

    /**
     * Rend le bouton sélectionné ou pas
     *
     * @param b Sélectionné ?
     */
    public void setSelected(boolean b) {
        selected = b;
        repaint();
    }

    /**
     * Dessine les composants du bouton
     *
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int taille = Math.min(getWidth(), getHeight()); // La taille du cercle
        int x = (Math.max(getWidth(), getHeight()) - taille) /2; // La position en x, en y c'est toujours 0
        if (selected) {
            g2.setColor(Theme.BUTTON_SELECTED.getColor(Parameters.getThemeName()));
            g2.fillOval(x, 0, taille, taille);
        }
        if (image != null) {
            g2.drawImage(image, x, 0, taille, taille, this);
        } else {
            super.paintComponent(g);
            g2.setColor(Theme.BORDER.getColor(Parameters.getThemeName()));
            g2.setStroke(new BasicStroke(1.6f));
            g2.drawOval(x, 0, taille, taille);
            g2.dispose();
        }
    }
}
