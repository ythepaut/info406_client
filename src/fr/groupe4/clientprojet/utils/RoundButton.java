package fr.groupe4.clientprojet.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Permet de créer des boutons rond avec ou sans image
 * Sans image -> un contour rond noir autour du bouton
 * Avec image -> l'image centrée sur le boutons, sans texte
 */
public class RoundButton extends JButton {
    /**
     * Si le bouton est sélectionné ou pas
     */
    private boolean selected;
    /**
     * L'image du bouton (pour bouton avec image)
     */
    private Image image;

    /**
     * Constructeur du bouton sans image
     *
     * @param text : le texte du bouton
     */
    public RoundButton(String text) {
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
     * @param file : L'image du bouton
     */
    public RoundButton(File file) {
        this(" ");
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rend le bouton sélectionné ou pas
     *
     * @param b : sélectionné ?
     */
    public void setSelected(boolean b) {
        this.selected = b;
        repaint();
    }

    /**
     * Dessine les composants du bouton
     *
     * @param g : Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int taille = Math.min(getWidth(), getHeight()); // La taille du cercle
        int x = (Math.max(getWidth(), getHeight()) - taille) /2; // La position en x, en y c'est toujours 0
        if (selected) {
            g2.setColor(new Color(84, 180, 255));
            g2.fillOval(x, 0, taille, taille);
        }
        if (image != null) {
            g2.drawImage(image, x, 0, taille, taille, this); // Le 13/28 et 5/28 est trouvé par tâtonnement
            //g2.drawImage(image, (int)((13.0/28.0)*taille), (int)((5.0/28.0)*taille), 2*(taille/3), 2*(taille/3), this); // Le 13/28 et 5/28 est trouvé par tâtonnement
        } else {
            super.paintComponent(g);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.6f));
            g2.drawOval(x, 0, taille, taille);
            g2.dispose();
        }
    }
}
