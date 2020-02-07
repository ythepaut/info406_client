package fr.groupe4.clientprojet.calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Composant du calendrier, vue
 *
 * @author Romain
 */
public class CalendarComponent extends JComponent implements Observer {
    private Calendar calendar;
    private BufferedImage img;

    public CalendarComponent(Calendar calendar) {
        this.calendar = calendar;
        calendar.addObserver(this);

        this.img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        this.update(null, null);
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void update(Observable obs, Object o) {
        repaint();
    }
}
