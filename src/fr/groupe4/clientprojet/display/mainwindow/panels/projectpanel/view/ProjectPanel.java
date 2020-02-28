package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;
import fr.groupe4.clientprojet.project.Project;
import fr.groupe4.clientprojet.project.ProjectList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Le panel des projets
 */
public class ProjectPanel extends JPanel {
    /**
     * Le projet qui est affiché
     */
    private Project project;

    /**
     * Le constructeur
     *
     * @param projectName : Le nom du projet
     */
    public ProjectPanel(String projectName) {
        Communication comm = Communication.builder().sleepUntilFinished().startNow().getProjectList().build();

        ProjectList list = (ProjectList) comm.getResult();

        for (Project p: list) {
            if (p.getName().equals(projectName)) {
                project = p;
            }
        }

        drawContent();
    }

    /**
     * Dessine le contenu
     */
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

        // Les slides
        ArrayList<String> slideName = new ArrayList<>();
        ArrayList<JPanel> slidePanel = new ArrayList<>();
        slideName.add("HOME"); slidePanel.add(homePanel());
        slideName.add("TASK"); slidePanel.add(taskPanel());
        slideName.add("MESSAGE"); slidePanel.add(messagePanel());

        Slide slides = new Slide(slidePanel, slideName);
        add(slides, BorderLayout.CENTER);
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
}
