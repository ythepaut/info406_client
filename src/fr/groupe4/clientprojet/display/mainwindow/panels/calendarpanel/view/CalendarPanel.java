package fr.groupe4.clientprojet.display.mainwindow.panels.calendarpanel.view;


import fr.groupe4.clientprojet.calendar.CalendarProject;
import fr.groupe4.clientprojet.calendar.CalendarComponent;

import javax.swing.*;
import java.awt.*;

public class CalendarPanel extends JPanel {

    public CalendarPanel() {
        drawContent();
    }

    private void drawContent() {
        setLayout(new BorderLayout());

        CalendarProject c = new CalendarProject(6, 2020);

        // Titre
        CalendarComponent calendarPanel = new CalendarComponent(c);

        add(calendarPanel, BorderLayout.CENTER);
    }
}
