package fr.groupe4.clientprojet.display.mainwindow.panels.calendarpanel.view;


import fr.groupe4.clientprojet.calendar.Calendar;
import fr.groupe4.clientprojet.calendar.CalendarComponent;

import javax.swing.*;
import java.awt.*;

public class CalendarPanel extends JPanel {

    public CalendarPanel() {
        drawContent();
    }

    private void drawContent() {
        setLayout(new BorderLayout());

        Calendar c = new Calendar(6, 2020);

        // Titre
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        CalendarComponent calendarPanel = new CalendarComponent(c);

        titlePanel.add(new JLabel("Calendrier"));

        add(titlePanel, BorderLayout.NORTH);

        add(calendarPanel);
    }
}
