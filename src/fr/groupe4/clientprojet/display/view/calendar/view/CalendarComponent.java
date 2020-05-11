package fr.groupe4.clientprojet.display.view.calendar.view;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.calendar.CalendarProject;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
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
    @NotNull
    private GenericCalendarComponent component;

    /**
     * Constructeur
     *
     * @param calendar Calendrier
     * @throws IllegalArgumentException Type de calendrier inconnu ou pas encore fait
     */
    public CalendarComponent(@NotNull CalendarProject calendar) throws IllegalArgumentException {
        calendar.addPropertyChangeListener(this);

        switch (calendar.getType()) {
            case DAY:
                Logger.error("Pas encore fait : jour");
                throw new IllegalArgumentException("Pas encore fait : jour");

            case WEEK:
                component = new CalendarComponentWeek(this, calendar);
                break;

            case MONTH:
                Logger.error("Pas encore fait : mois");
                throw new IllegalArgumentException("Pas encore fait : mois");

            case YEAR:
                Logger.error("Pas encore fait : année");
                throw new IllegalArgumentException("Pas encore fait : année");

            default:
                Logger.error("Type de calendrier inconnu");
                throw new IllegalArgumentException("Type de calendrier inconnu");
        }

        component.init();
    }

    /**
     * Méthode appelée pour peindre le calendrier
     */
    @Override
    public void paintComponent(Graphics g) {
        component.paintComponent(g);
    }

    /**
     * Update observer / observable
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        removeAll();
        component.init();
        repaint();
    }
}
