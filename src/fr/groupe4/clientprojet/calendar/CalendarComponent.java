package fr.groupe4.clientprojet.calendar;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.loaddialog.view.LoadDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.timeslot.TimeSlot;
import fr.groupe4.clientprojet.model.timeslot.TimeSlotList;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

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

    private JLabel[] daysTitle;
    private JPanel[] daysPanel;

    private TimeSlotList timeSlots;

    public CalendarComponent(CalendarProject calendar) {
        this.calendar = calendar;
        calendar.addPropertyChangeListener(this);

        Communication c = Communication.builder()
                .getUserTimeSlotList(LocalDate.of(2020, 3, 2), LocalDate.of(2020, 3, 9))
                .startNow()
                .sleepUntilFinished()
                .build();

        timeSlots = (TimeSlotList) c.getResult();

        Logger.info(timeSlots);

        initComponent();
    }

    private void initWeek() {
        daysTitle = new JLabel[7];
        daysPanel = new JPanel[7];

        setLayout(new GridLayout(1, 7));

        for (int i = 0; i < 7; i++) {
            daysTitle[i] = new JLabel("", SwingConstants.CENTER);
            daysPanel[i] = new JPanel();
            daysPanel[i].setLayout(null);

            JPanel dayTitlePanel = new JPanel(new BorderLayout());
            dayTitlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            dayTitlePanel.add(daysTitle[i]);
            daysTitle[i].setOpaque(true);
            daysTitle[i].setBackground(Color.WHITE);

            daysPanel[i].setBackground(Color.WHITE);
            daysPanel[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            Font f = daysTitle[i].getFont();
            daysTitle[i].setFont(f.deriveFont(f.getStyle() | Font.BOLD)); // Met le label en gras

            JPanel generalPanel = new JPanel(new BorderLayout());
            generalPanel.add(daysTitle[i], BorderLayout.NORTH);
            generalPanel.add(daysPanel[i], BorderLayout.CENTER);

            add(generalPanel);
        }
    }

    private void initComponent() throws IllegalArgumentException {
        switch (type) {
            case DAY:
                break;

            case WEEK:
                initWeek();
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
     * Méthode appelée pour peindre le calendrier
     */
    @Override
    public void paintComponent(Graphics g) throws IllegalArgumentException {
        switch (type) {
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
            JLabel dayLabel = daysTitle[i];
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
