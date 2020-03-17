package fr.groupe4.clientprojet.display.view.calendar.view;

import fr.groupe4.clientprojet.model.calendar.CalendarProject;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;

import java.awt.Graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Composant du calendrier, vue
 *
 * @author Romain
 */
public class CalendarComponent extends JPanel implements PropertyChangeListener {
    /**
     * Composant à afficher
     */
    private GenericCalendarComponent component;

    /**
     * Constructeur
     *
     * @param calendar Calendrier
     *
     * @throws IllegalArgumentException Type de calendrier inconnu ou pas encore fait
     */
    public CalendarComponent(@NotNull CalendarProject calendar) throws IllegalArgumentException {
        calendar.addPropertyChangeListener(this);

        switch (calendar.getType()) {
            case DAY:
                throw new IllegalArgumentException("Pas encore fait : jour");

            case WEEK:
                component = new CalendarComponentWeek(this, calendar);
                break;

            case MONTH:
                throw new IllegalArgumentException("Pas encore fait : mois");

            case YEAR:
                throw new IllegalArgumentException("Pas encore fait : année");

            default:
                throw new IllegalArgumentException("Type de calendrier inconnu");
        }

        component.init();
    }

    /**
     * Méthode appelée pour peindre le calendrier
     */
    @Override
    public void paintComponent(Graphics g) throws IllegalArgumentException {
        component.paintComponent(g);
    }

    /**
     * Update
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        removeAll();
        component.init();
        repaint();
    }
}
