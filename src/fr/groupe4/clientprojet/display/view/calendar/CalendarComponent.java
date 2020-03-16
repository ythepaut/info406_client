package fr.groupe4.clientprojet.display.view.calendar;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.calendar.CalendarProject;
import fr.groupe4.clientprojet.model.calendar.enums.CalendarType;
import fr.groupe4.clientprojet.model.timeslot.TimeSlot;
import fr.groupe4.clientprojet.model.timeslot.TimeSlotList;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.DayOfWeek;
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
    @NotNull
    private CalendarType type;

    /**
     * Labels des titres
     */
    @NotNull
    private JLabel[] daysTitle;

    /**
     * Panels des jours
     */
    @NotNull
    private JPanel[] daysPanel;

    /**
     * Contraintes du GridBagLayout
     */
    @NotNull
    private GridBagConstraints[] constraints;

    /**
     * Liste de tous les créneaux
     */
    private TimeSlotList allTimeSlots;

    public CalendarComponent(CalendarProject calendar) {
        this(calendar, CalendarType.WEEK);
    }

    public CalendarComponent(CalendarProject calendar, @NotNull CalendarType type) throws IllegalArgumentException {
        this.calendar = calendar;
        calendar.addPropertyChangeListener(this);

        this.type = type;

        // LocalDate now = LocalDate.now();
        LocalDate now = LocalDate.of(2020, 3, 5);

        LocalDate from = null, to = null;

        switch (type) {
            case DAY:
                break;

            case WEEK:
                from = now.with(DayOfWeek.MONDAY);
                to = now.with(DayOfWeek.SUNDAY);
                break;

            case MONTH:
                break;

            case YEAR:
                break;

            default:
                throw new IllegalArgumentException("Type de calendrier inconnu");
        }

        Communication c = Communication.builder()
                .getUserTimeSlotList(from, to)
                .startNow()
                .sleepUntilFinished()
                .build();

        allTimeSlots = (TimeSlotList) c.getResult();

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

    private void initWeek() {
        daysTitle = new JLabel[7];
        daysPanel = new JPanel[7];
        constraints = new GridBagConstraints[7];

        setLayout(new GridLayout(1, 7));

        final String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

        for (int i = 0; i < 7; i++) {
            daysTitle[i] = new JLabel("<html><div style='color:blue'>" + days[i] + "</div></html>", SwingConstants.CENTER);
            daysPanel[i] = new JPanel();
            daysPanel[i].setLayout(new GridBagLayout());

            constraints[i] = new GridBagConstraints();

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

    /**
     * Méthode appelée pour peindre le calendrier
     */
    @Override
    public void paintComponent(Graphics g) throws IllegalArgumentException {
        for (JPanel panel : daysPanel) {
            panel.removeAll();
        }

        switch (type) {
            case DAY:
                Logger.warning("Type de calendrier pas encore supporté");
                break;

            case WEEK:
                paintWeek(g);
                break;

            case MONTH:
                Logger.warning("Type de calendrier pas encore supporté");
                break;

            case YEAR:
                Logger.warning("Type de calendrier pas encore supporté");
                break;

            default:
                Logger.error("Type de calendrier inconnu :", type);
                throw new IllegalArgumentException("Type de calendrier inconnu");
        }

        for (int i=0; i<daysPanel.length; i++) {
            daysPanel[i].updateUI();
        }
    }

    /**
     * Méthode peignant une semaine
     */
    private void paintWeek(Graphics g) {
        final TimeSlotList[] weekTimeSlots = new TimeSlotList[7];

        for (int i=0; i<weekTimeSlots.length; i++) {
            weekTimeSlots[i] = new TimeSlotList();
        }

        for (TimeSlot timeSlot : allTimeSlots) {
            final int dayPlacement = timeSlot.getStartTime().getDayOfWeek().getValue() - 1;

            weekTimeSlots[dayPlacement].add(timeSlot);
        }

        boolean[] present;
        JPanel subPanel;
        JPanel empty;

        for (int day=0; day<7; day++) {
            TimeSlotList dayTimeSlots = weekTimeSlots[day];
            present = new boolean[(TimeSlot.HIGHEST_TIME - TimeSlot.LOWEST_TIME) / TimeSlot.TIME_SCALE];

            for (TimeSlot timeSlot : dayTimeSlots) {
                final int startTime = Math.max(TimeSlot.LOWEST_TIME,
                        Math.min(TimeSlot.HIGHEST_TIME,
                                timeSlot.getStartTime().toLocalTime().toSecondOfDay()));

                final int duration = Math.max(0,
                        Math.min(TimeSlot.HIGHEST_TIME - startTime,
                                timeSlot.getEndTime().toLocalTime().toSecondOfDay() - startTime));

                final int startPlacement = (startTime - TimeSlot.LOWEST_TIME) / TimeSlot.TIME_SCALE;
                final int durationPlacement = (int) Math.ceil(((double) duration) / ((double) TimeSlot.TIME_SCALE));

                for (int i = startPlacement; i < startPlacement + durationPlacement; i++) {
                    if (present[i]) {
                        Logger.warning("Chevauchement pas encore supporté !");
                    } else {
                        present[i] = true;
                    }
                }
            }

            for (int index=0; index<dayTimeSlots.size(); index++) {
                TimeSlot timeSlot = dayTimeSlots.get(index);

                final int startTime = Math.max(TimeSlot.LOWEST_TIME,
                        Math.min(TimeSlot.HIGHEST_TIME,
                                timeSlot.getStartTime().toLocalTime().toSecondOfDay()));

                final int duration = Math.max(0,
                        Math.min(TimeSlot.HIGHEST_TIME - startTime,
                                timeSlot.getEndTime().toLocalTime().toSecondOfDay() - startTime));

                final int startPlacement = (startTime - TimeSlot.LOWEST_TIME) / TimeSlot.TIME_SCALE;
                final int durationPlacement = (int) Math.ceil(((double) duration) / ((double) TimeSlot.TIME_SCALE));

                constraints[day].weightx = 1.0;
                constraints[day].weighty = ((float) durationPlacement) / ((float) present.length);
                constraints[day].fill = GridBagConstraints.BOTH;
                constraints[day].gridwidth = GridBagConstraints.REMAINDER;
                constraints[day].gridheight = durationPlacement;

                subPanel = new JPanel(new GridLayout(2, 1));
                subPanel.setBackground(Color.PINK);
                subPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
                subPanel.add(new JLabel("Début : " + timeSlot.getStartTime().toLocalTime()));
                subPanel.add(new JLabel("Fin : " + timeSlot.getEndTime().toLocalTime()));

                constraints[day].gridx = 0;
                constraints[day].gridy = startPlacement;

                if (startPlacement+durationPlacement == present.length-1) {
                    constraints[day].anchor = GridBagConstraints.PAGE_END;
                }

                daysPanel[day].add(subPanel, constraints[day]);
            }

            constraints[day].weightx = 1.0;
            constraints[day].weighty = 1.0;
            constraints[day].fill = GridBagConstraints.BOTH;
            constraints[day].gridwidth = GridBagConstraints.REMAINDER;
            constraints[day].gridheight = 1;
            constraints[day].weighty = 1.0 / ((float) present.length);

            for (int i=0; i<present.length; i++) {
                if (!present[i]) {
                    empty = new JPanel();
                    empty.setBackground(Color.WHITE);
                    constraints[day].gridx = 0;
                    constraints[day].gridy = i;

                    if (i == present.length-1) {
                        constraints[day].anchor = GridBagConstraints.PAGE_END;
                    }

                    daysPanel[day].add(empty, constraints[day]);
                }
            }
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
