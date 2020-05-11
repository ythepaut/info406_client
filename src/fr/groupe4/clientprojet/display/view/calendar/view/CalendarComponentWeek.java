package fr.groupe4.clientprojet.display.view.calendar.view;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.calendar.CalendarProject;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.timeslot.TimeSlot;
import fr.groupe4.clientprojet.model.timeslot.TimeSlotList;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Calendrier pour la semaine
 *
 * @author Romain
 */
class CalendarComponentWeek extends GenericCalendarComponent {
    /**
     * Constructeur
     *
     * @param parent Fenêtre parente
     * @param calendar Calendrier
     */
    CalendarComponentWeek(@NotNull JPanel parent, @NotNull CalendarProject calendar) {
        super(parent, calendar);
    }

    /**
     * Initialisation (ici, les labels des jours)
     */
    @Override
    public void init() {
        allTimeSlots = calendar.getTimeSlots();

        daysTitle = new JLabel[7];
        daysPanel = new JPanel[7];
        constraints = new GridBagConstraints[7];

        parent.setLayout(new GridLayout(1, 7));

        final Object[][] days = {
                {"Lundi", DayOfWeek.MONDAY},
                {"Mardi", DayOfWeek.TUESDAY},
                {"Mercredi", DayOfWeek.WEDNESDAY},
                {"Jeudi", DayOfWeek.THURSDAY},
                {"Vendredi", DayOfWeek.FRIDAY},
                {"Samedi", DayOfWeek.SATURDAY},
                {"Dimanche", DayOfWeek.SUNDAY}
        };

        for (int i = 0; i < 7; i++) {
            LocalDate date = calendar.getDate().with((DayOfWeek) days[i][1]);

            daysTitle[i] = new JLabel(
                    "<html><div style='color:"
                            + (LocalDate.now().isEqual(date) ? "red":"blue")
                            + "'>"
                            + days[i][0]
                            + " "
                            + String.format("%02d", date.getDayOfMonth())
                            + "/"
                            + String.format("%02d", date.getMonthValue())
                            + "</div></html>",
                    SwingConstants.CENTER
            );

            daysPanel[i] = new JPanel();
            daysPanel[i].setLayout(new GridBagLayout());

            constraints[i] = new GridBagConstraints();

            JPanel dayTitlePanel = new JPanel(new BorderLayout());
            dayTitlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            dayTitlePanel.add(daysTitle[i]);
            daysTitle[i].setOpaque(true);
            daysTitle[i].setBackground(Theme.FOND.getColor(Parameters.getThemeName()));

            daysPanel[i].setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
            daysPanel[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            Font f = daysTitle[i].getFont();
            daysTitle[i].setFont(f.deriveFont(f.getStyle() | Font.BOLD)); // Met le label en gras

            JPanel generalPanel = new JPanel(new BorderLayout());
            generalPanel.add(daysTitle[i], BorderLayout.NORTH);
            generalPanel.add(daysPanel[i], BorderLayout.CENTER);

            parent.add(generalPanel);
        }
    }

    /**
     * Découpage en 7 sous-listes pour chaque jour
     *
     * @return Les 7 sous-listes
     */
    private TimeSlotList[] getWeekTimeSlots() {
        final TimeSlotList[] weekTimeSlots = new TimeSlotList[7];

        for (int i=0; i<weekTimeSlots.length; i++) {
            weekTimeSlots[i] = new TimeSlotList();
        }

        for (TimeSlot timeSlot : allTimeSlots) {
            final int dayPlacement = timeSlot.getStartTime().getDayOfWeek().getValue() - 1;

            weekTimeSlots[dayPlacement].add(timeSlot);
        }

        return weekTimeSlots;
    }

    /**
     * Affichage
     *
     * @param g Graphics, où afficher
     */
    @Override
    protected void paintComponent(Graphics g) {
        for (JPanel panel : daysPanel) {
            // Suppression de ce qu'il y avait avant
            panel.removeAll();
        }

        final TimeSlotList[] weekTimeSlots = getWeekTimeSlots();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Calcul des chevauchements et des créneaux qu'il faudra combler avec un panel vide

        boolean[] present;
        JPanel subPanel;
        JPanel empty;

        for (int day=0; day<7; day++) {
            TimeSlotList dayTimeSlots = weekTimeSlots[day];
            present = new boolean[(TimeSlot.HIGHEST_TIME - TimeSlot.LOWEST_TIME) / TimeSlot.TIME_SCALE];

            for (TimeSlot timeSlot : dayTimeSlots) {
                final int startTime =
                        Math.max(TimeSlot.LOWEST_TIME,
                        Math.min(TimeSlot.HIGHEST_TIME,
                                timeSlot.getStartTime().toLocalTime().toSecondOfDay()));

                final int duration =
                        Math.max(0,
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

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Remplissage avec les créneaux

            for (TimeSlot timeSlot : dayTimeSlots) {
                final int startTime =
                        Math.max(TimeSlot.LOWEST_TIME,
                        Math.min(TimeSlot.HIGHEST_TIME,
                                timeSlot.getStartTime().toLocalTime().toSecondOfDay()));

                final int duration =
                        Math.max(0,
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

                if (startPlacement + durationPlacement == present.length - 1) {
                    constraints[day].anchor = GridBagConstraints.PAGE_END;
                }

                daysPanel[day].add(subPanel, constraints[day]);
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Remplissage avec les panels vides

            constraints[day].weightx = 1.0;
            constraints[day].weighty = 1.0;
            constraints[day].fill = GridBagConstraints.BOTH;
            constraints[day].gridwidth = GridBagConstraints.REMAINDER;
            constraints[day].gridheight = 1;
            constraints[day].weighty = 1.0 / ((float) present.length);

            for (int i = 0; i < present.length; i++) {
                if (!present[i]) {
                    empty = new JPanel();
                    empty.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
                    constraints[day].gridx = 0;
                    constraints[day].gridy = i;

                    if (i == present.length - 1) {
                        constraints[day].anchor = GridBagConstraints.PAGE_END;
                    }

                    daysPanel[day].add(empty, constraints[day]);
                }
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Mise à jour finale

        for (JPanel jPanel : daysPanel) {
            jPanel.updateUI();
        }
    }
}
