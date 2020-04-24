package fr.groupe4.clientprojet.display.dialog.loaddialog.view;

import fr.groupe4.clientprojet.utils.Location;

import javax.imageio.ImageIO;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

/**
 * Canvas qui est dans le LoadDialog
 */
public class LoadCanvas extends Canvas {
    /**
     * L'angle de départ de l'arc de cercle
     */
    private int angle;

    /**
     * Le constructeur
     */
    public LoadCanvas() {
        angle = 0;
    }

    /**
     * Dessine l'image de chargement
     *
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(getWidth() / 2, getHeight() / 2); // Translate au milieu
        int width = 50;
        int x = - (width /2);
        int y = - (width /2);
        g2.rotate(Math.toRadians(angle));
        try {
            g2.drawImage(ImageIO.read(new File(Location.getPath() + "/data/img/loading.png")),
                    x, y, width, width, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        angle += 2;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }
}
