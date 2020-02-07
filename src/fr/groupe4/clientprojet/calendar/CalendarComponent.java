package fr.groupe4.clientprojet.calendar;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import fr.groupe4.clientprojet.calendar.CalendarType;

/**
 * Composant du calendrier, vue
 *
 * @author Romain
 */
public class CalendarComponent extends JComponent implements Observer {
    /**
     * Calendrier associé
     */
    private Calendar calendar;

    /**
     * Type de calendrier
     */
    private CalendarType type = CalendarType.WEEK;

    /**
     * @deprecated
     */
    private Date startDate, endDate;

    public CalendarComponent(Calendar calendar) {
        this.calendar = calendar;
        calendar.addObserver(this);

        this.update(null, null);
    }

    /**
     * Méthode appelée pour peindre le calendrier
     */
    @Override
    public void paintComponent(Graphics g) {
        switch (this.type) {
            case DAY:
                break;

            case WEEK:
                paintWeek(g);
                break;

            case MONTH:
                break;

            case YEAR:
                break;

            default:
                throw new IllegalArgumentException("Type de calendrier inconnu");
        }
    }

    /**
     * Méthode peignant une semaine
     */
    private void paintWeek(Graphics g) {
        final int xMargin = 10, yMargin = 5;

        g.setColor(Color.RED);
        g.drawRect(0, 0, getWidth(), getHeight());

        final String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

        for (int day=0; day<7; day++) {
            final int weekWidth = getWidth()-2*xMargin;
            final int dayXMargin = 3;
            final int dayX = (weekWidth*day)/7 + xMargin + dayXMargin;
            final int dayWidth = weekWidth*1/7 - 2*dayXMargin;
            final int dayTotalHeight = getHeight()-2*yMargin;
            final int dayLabelHeight = dayTotalHeight/10;

            g.setColor(Color.GREEN);
            g.drawRect(dayX, yMargin, dayWidth, dayTotalHeight);

            g.setColor(Color.BLUE);
            g.drawRect(dayX, yMargin, dayWidth, dayLabelHeight);

            int sw = g.getFontMetrics().stringWidth(days[day]), sh = g.getFontMetrics().getHeight();
            g.setColor(Color.BLACK);
            g.drawString(days[day], dayX + (dayWidth-sw)/2, yMargin + (dayLabelHeight+sh/2)/2);
        }
    }

    /**
     * Update du pattern Observable/Observer
     */
    @Override
    public void update(Observable obs, Object o) {
        repaint();
    }
}
