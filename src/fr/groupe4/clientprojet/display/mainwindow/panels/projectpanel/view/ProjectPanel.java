package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller.EventProjectPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.enums.ProjectSlide;
import fr.groupe4.clientprojet.display.view.RoundButton;
import fr.groupe4.clientprojet.project.Project;
import fr.groupe4.clientprojet.project.ProjectList;

import javax.swing.*;
import java.awt.*;

public class ProjectPanel extends JPanel {
    private ProjectSlide slide;
    private EventProjectPanel eventProjectPanel;
    private Project project;

    public ProjectPanel(String projectName) {
        slide = ProjectSlide.HOME;
        eventProjectPanel = new EventProjectPanel(this);

        Communication comm = Communication.builder().sleepUntilFinished().startNow().getProjectList().build();

        ProjectList list = (ProjectList) comm.getResult();

        for (Project p: list) {
            if (p.getName().equals(projectName)) {
                project = p;
            }
        }

        drawContent();
    }

    private void drawContent() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        JLabel title = new JLabel(project.getName());
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        titlePanel.add(title, c);

        add(titlePanel, BorderLayout.NORTH);

        // Partie centrale (onglets)
        JPanel slidesPanel = new JPanel(new BorderLayout());
        slidesPanel.setBackground(Color.WHITE);

        // Bouton gauche
        RoundButton leftButton = new RoundButton("<");
        leftButton.setActionCommand(EventProjectPanel.LEFT);
        leftButton.addActionListener(eventProjectPanel);
        slidesPanel.add(leftButton, BorderLayout.WEST);
        // Bouton droite
        RoundButton rightButton = new RoundButton(">");
        rightButton.setActionCommand(EventProjectPanel.RIGHT);
        rightButton.addActionListener(eventProjectPanel);
        slidesPanel.add(rightButton, BorderLayout.EAST);
        // Boutons haut
        JPanel topButtons = new JPanel(new GridBagLayout());
        topButtons.setBackground(Color.WHITE);
        c.gridx = c.gridy = 0;
        JButton homeButton = new JButton("HOME");
        homeButton.setActionCommand(ProjectSlide.HOME.getNbString());
        homeButton.addActionListener(eventProjectPanel);
        topButtons.add(homeButton, c);
        c.gridx++;
        JButton taskButton = new JButton("TASK");
        taskButton.setActionCommand(ProjectSlide.TASK.getNbString());
        taskButton.addActionListener(eventProjectPanel);
        topButtons.add(taskButton, c);
        c.gridx++;
        JButton messagePanel = new JButton("MESSAGE");
        messagePanel.setActionCommand(ProjectSlide.MESSAGE.getNbString());
        messagePanel.addActionListener(eventProjectPanel);
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
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("HOME"), BorderLayout.NORTH);
        panel.add(new JLabel(project.getDescription()), BorderLayout.CENTER);
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
        this.slide = ProjectSlide.getEnum(Math.max(ProjectSlide.HOME.getNb(), Math.min(slide, ProjectSlide.MESSAGE.getNb())));
    }

    /**
     * Renvoie le slide sur lequel on est
     *
     * @return : le slide
     */
    public ProjectSlide getSlide() {
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
