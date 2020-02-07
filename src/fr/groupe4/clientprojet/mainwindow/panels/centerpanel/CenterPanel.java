package fr.groupe4.clientprojet.mainwindow.panels.centerpanel;

import javax.swing.*;
import java.awt.*;

import fr.groupe4.clientprojet.calendar.*;
import fr.groupe4.clientprojet.utils.RoundButton;

public class CenterPanel extends JPanel {
    public static final String CALENDAR = "calendar", USER = "user";
    private String view;
    public static final int HOME = 0, TASK = 1, MESSAGE = 2;
    private int slide = HOME;

    private EventCenterPanel eventCenterPanel;

    public CenterPanel(String view) {
        this.view = view;

        setBackground(Color.WHITE);
        eventCenterPanel = new EventCenterPanel(this);

        drawContent();
    }

    public void setView(String view) {
        this.view = view;

        // On redéssine
        redraw();
    }

    public String getView() {
        return view;
    }

    private void drawContent() {
        switch (view) {
            case CALENDAR:
                drawCalendar();
                break;

            case USER:
                drawUser();
                break;

            default:
                drawProject();
        }
    }

    private void drawCalendar() {
        setLayout(new BorderLayout());

        Calendar c = new Calendar();

        // Titre
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        CalendarComponent calendarPanel = new CalendarComponent(c);

        titlePanel.add(new JLabel(view));

        add(titlePanel, BorderLayout.NORTH);

        add(calendarPanel);
    }

    private void drawUser() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        titlePanel.add(new JLabel(view));

        add(titlePanel, BorderLayout.NORTH);
    }

    private void drawProject() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        JLabel title = new JLabel(view);
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        titlePanel.add(title, c);

        add(titlePanel, BorderLayout.NORTH);

        // Partie centrale (onglets)
        JPanel slidesPanel = new JPanel(new BorderLayout());
        slidesPanel.setBackground(Color.WHITE);

        // Bouton gauche
        RoundButton leftButton = new RoundButton("<");
        leftButton.setActionCommand(EventCenterPanel.LEFT);
        leftButton.addActionListener(eventCenterPanel);
        slidesPanel.add(leftButton, BorderLayout.WEST);
        // Bouton droite
        RoundButton rightButton = new RoundButton(">");
        rightButton.setActionCommand(EventCenterPanel.RIGHT);
        rightButton.addActionListener(eventCenterPanel);
        slidesPanel.add(rightButton, BorderLayout.EAST);
        // Boutons haut
        JPanel topButtons = new JPanel(new GridBagLayout());
        topButtons.setBackground(Color.WHITE);
        c.gridx = c.gridy = 0;
        JButton homeButton = new JButton("HOME");
        homeButton.setActionCommand("" + HOME);
        homeButton.addActionListener(eventCenterPanel);
        topButtons.add(homeButton, c);
        c.gridx++;
        JButton taskButton = new JButton("TASK");
        taskButton.setActionCommand("" + TASK);
        taskButton.addActionListener(eventCenterPanel);
        topButtons.add(taskButton, c);
        c.gridx++;
        JButton messagePanel = new JButton("MESSAGE");
        messagePanel.setActionCommand("" + MESSAGE);
        messagePanel.addActionListener(eventCenterPanel);
        topButtons.add(messagePanel, c);

        slidesPanel.add(topButtons, BorderLayout.NORTH);

        switch (slide) {
            case HOME:
                slidesPanel.add(homePanel(), BorderLayout.CENTER);
                break;

            case TASK:
                slidesPanel.add(taskPanel(), BorderLayout.CENTER);
                break;

            case MESSAGE:
                slidesPanel.add(messagePanel(), BorderLayout.CENTER);
                break;

            default:
        }

        add(slidesPanel, BorderLayout.CENTER);
    }

    private JPanel homePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("HOME"));
        // TODO : Construire panel accueil
        return panel;
    }

    private JPanel taskPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("TÂCHES"));
        // TODO : Construire panel tâches
        return panel;
    }

    private JPanel messagePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("MESSAGES"));
        // TODO : Construire panel messagerie
        return panel;
    }

    public void setSlide(int slide) {
        this.slide = Math.max(HOME, Math.min(slide, MESSAGE));
    }

    public int getSlide() {
        return slide;
    }

    public void redraw() {
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }
}
