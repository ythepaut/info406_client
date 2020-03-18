package fr.groupe4.clientprojet.display.mainwindow.panels.calendarpanel.view;

import fr.groupe4.clientprojet.display.view.calendar.controller.CalendarButtonListener;
import fr.groupe4.clientprojet.display.view.calendar.controller.CalendarKeyAdapter;
import fr.groupe4.clientprojet.model.calendar.CalendarProject;
import fr.groupe4.clientprojet.display.view.calendar.view.CalendarComponent;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.model.calendar.enums.CalendarType;

import javax.swing.*;
import java.awt.*;

public class CalendarPanel extends DrawPanel {

    public CalendarPanel() {
        drawContent();
    }

    @Override
    protected void drawContent() {
        setLayout(new BorderLayout());

        CalendarProject c = new CalendarProject(CalendarType.WEEK);
        CalendarComponent calendarPanel = new CalendarComponent(c);

        JButton b1 = new JButton("<-");
        JButton b2 = new JButton("->");

        b1.addActionListener(new CalendarButtonListener(c, CalendarButtonListener.PREVIOUS));
        b2.addActionListener(new CalendarButtonListener(c, CalendarButtonListener.NEXT));

        add(calendarPanel, BorderLayout.CENTER);
        add(b1, BorderLayout.WEST);
        add(b2, BorderLayout.EAST);
    }
}
