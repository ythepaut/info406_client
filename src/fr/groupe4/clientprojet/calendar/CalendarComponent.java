package fr.groupe4.clientprojet.calendar;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CalendarComponent extends JComponent implements Observer {
    Calendar calendar;
    Image img;

    public CalendarComponent(Calendar calendar) {
        this.calendar = calendar;
        calendar.addObserver(this);
        this.update(null, null);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    @Override
    public void update(Observable obs, Object o) {
        return;
        /*
        int x = 100, y = 100;

        Graphics g = img.getGraphics();
        g.fillOval(x, y, 30, 30);
        g.dispose();*/
    }
}
