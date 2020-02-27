package fr.groupe4.clientprojet.calendar;

import fr.groupe4.clientprojet.communication.Communication;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Composant du calendrier, vue
 *
 * @author Romain
 */
public class CalendarComponent extends JComponent implements PropertyChangeListener {
    /**
     * Calendrier associé
     */
    private CalendarProject calendar;

    /**
     * Type de calendrier
     */
    private CalendarType type = CalendarType.WEEK;

    private ArrayList<JComponent[]> daysComponent;

    public CalendarComponent(CalendarProject calendar) {
        this.calendar = calendar;
        calendar.addPropertyChangeListener(this);

        daysComponent = new ArrayList<>();

        // Communication c = Communication.builder().getTimeSlotList(new GregorianCalendar(2020, GregorianCalendar.JANUARY, 1), new GregorianCalendar(2020, GregorianCalendar.DECEMBER, 31)).startNow().sleepUntilFinished().build();


        setLayout(new GridLayout(1, 7));

        for (int i=0; i<7; i++) {
            JPanel dayPanel = new JPanel(new BorderLayout());

            JLabel panelLabel = new JLabel("", SwingConstants.CENTER);
            JPanel panelContent = new JPanel();


            panelLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            panelContent.setBorder(BorderFactory.createLineBorder(Color.black));

            panelLabel.setOpaque(true);
            panelLabel.setBackground(Color.WHITE);
            panelContent.setBackground(Color.WHITE);

            Font f = panelLabel.getFont();
            panelLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD)); // Met le label en gras

            daysComponent.add(new JComponent[] {panelLabel, panelContent});
            dayPanel.add(panelLabel, BorderLayout.NORTH);
            dayPanel.add(panelContent, BorderLayout.CENTER);
            add(dayPanel);
        }
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
        final String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

        for (int i=0; i<7; i++) {
            JComponent[] dayComponents = daysComponent.get(i);

            JLabel dayLabel = (JLabel) dayComponents[0];
            dayLabel.setText("<html><div style='color:blue'>" + days[i] + "</div></html>");
        }
    }

    /**
     * Update
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }
}
