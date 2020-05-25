package fr.groupe4.clientprojet.display.dialog.loaddialog.view;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.utils.Location;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadCanvas extends Canvas {
    private int angle;

    public LoadCanvas() {
        angle = 0;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Theme.FOND.getColor());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.translate(getWidth() / 2, getHeight() / 2);

        int width = 50;
        int x = -(width / 2);
        int y = -(width / 2);
        g2.rotate(Math.toRadians(angle));
        try {
            g2.drawImage(ImageIO.read(new File(Location.getImgDataPath() + "/loading.png")), x, y, width, width, this);
        } catch (IOException e) {
            Logger.error(e);
        }
        angle += 2;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Logger.error(e);
        }
        repaint();
    }
}
