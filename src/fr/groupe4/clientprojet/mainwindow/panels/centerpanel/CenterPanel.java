package fr.groupe4.clientprojet.mainwindow.panels.centerpanel;

import javax.swing.*;
import java.awt.*;

import fr.groupe4.clientprojet.calendar.*;
import fr.groupe4.clientprojet.utils.RoundButton;

/**
 * Créé le panel du centre de la fenêtre
 */
public class CenterPanel extends JPanel {
    /**
     * Variables statiques destinées à disparaître au profit d'une énumeration
     */
    public static final String CALENDAR = "calendar", USER = "user";
    /**
     * La vue sur laquelle on est
     */
    private String view;
    /**
     * Variables statiques destinées à disparaître au profit d'une énumeration
     */
    public static final int HOME = 0, TASK = 1, MESSAGE = 2;
    /**
     * Le slide sur lequel on est
     */
    private int slide = HOME;

    /**
     * le listener du panel
     */
    private EventCenterPanel eventCenterPanel;

    /**
     * Le constructeur
     *
     * @param view : la vue sur laquelle se mettre
     */
    public CenterPanel(String view) {
        this.view = view;

        setBackground(Color.WHITE);
        eventCenterPanel = new EventCenterPanel(this);

        drawContent();
    }

    /**
     * Définie la vue du panel central
     *
     * @param view : la vue
     */
    public void setView(String view) {
        this.view = view;

        // On redéssine
        redraw();
    }

    /**
     * Renvoie la vue sur laquelle on est
     *
     * @return : la vue
     */
    public String getView() {
        return view;
    }

    /**
     * Dessine le contenu du panel
     */
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

    /**
     * Dessine le calendrier
     */
    private void drawCalendar() {
        setLayout(new BorderLayout());

        Calendar c = new Calendar(6, 2020);

        // Titre
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        CalendarComponent calendarPanel = new CalendarComponent(c);

        titlePanel.add(new JLabel(view));

        add(titlePanel, BorderLayout.NORTH);

        add(calendarPanel);
    }

    /**
     * Dessine la vue 'profil'
     */
    private void drawUser() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        titlePanel.add(new JLabel(view));

        add(titlePanel, BorderLayout.NORTH);
    }

    /**
     * Dessine la vue 'projet' avec ses slides
     */
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

    /**
     * Dessine le premier slide du projet
     *
     * @return : le jpanel
     */
    private JPanel homePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("HOME"));
        // TODO : Construire panel accueil
        return panel;
    }

    /**
     * Dessine le slide des tâches du projet
     *
     * @return : le jpanel
     */
    private JPanel taskPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("TÂCHES"));
        // TODO : Construire panel tâches
        return panel;
    }

    /**
     * Dessine le slide de la messagerie du projet
     *
     * @return : le jpanel
     */
    private JPanel messagePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("MESSAGES"));
        // TODO : Construire panel messagerie
        return panel;
    }

    /**
     * Défini le slide sur lequel on est
     *
     * @param slide : le slide
     */
    public void setSlide(int slide) {
        this.slide = Math.max(HOME, Math.min(slide, MESSAGE));
    }

    /**
     * Renvoie le slide sur lequel on est
     *
     * @return : le slide
     */
    public int getSlide() {
        return slide;
    }

    /**
     * Redessine le panel
     */
    public void redraw() {
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }
}
