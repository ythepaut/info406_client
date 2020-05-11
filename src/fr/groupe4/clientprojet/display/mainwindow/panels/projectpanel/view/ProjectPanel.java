package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller.RightClicMenuProjectListener;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.messagepanel.view.MessagePanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.taskprojectpanel.view.TaskProjectPanel;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.display.view.slide.SlideItem;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.project.ProjectList;

import javax.swing.*;
import java.awt.*;

/**
 * Panel des projets
 */
public class ProjectPanel extends DrawPanel {
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

        assert list != null;
        for (Project p : list) {
            if (p.getName().equals(projectName)) {
                project = p;
            }
        }

        addMouseListener(new RightClicMenuProjectListener(this));

        drawContent();
    }

    /**
     * Dessine le contenu
     */
    @Override
    protected void drawContent() {
        setLayout(new BorderLayout());
        setBackground(Theme.FOND.getColor(Parameters.getThemeName()));

        // Titre
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        JLabel title = new JLabel(project.getName());
        title.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        titlePanel.add(title, c);

        add(titlePanel, BorderLayout.NORTH);

        // Les slides
        Slide slides = new Slide();
        slides.addSlide(new SlideItem("HOME", homePanel()));
        slides.addSlide(new SlideItem("TASK", taskPanel()));
        slides.addSlide(new SlideItem("MESSAGE", messagePanel()));

        add(slides, BorderLayout.CENTER);
    }

    /**
     * Dessine le premier slide du projet
     *
     * @return Le JPanel
     */
    private JPanel homePanel() {
        // TODO : Construire panel accueil
        return new HomeProjectPanel(project);
    }

    /**
     * Dessine le slide des tâches du projet
     *
     * @return JPanel des tâches
     */
    private JPanel taskPanel() {
        return new TaskProjectPanel(project);
    }

    /**
     * Dessine le slide de la messagerie du projet
     *
     * @return JPanel de la messagerie
     */
    private JPanel messagePanel() {
        MessagePanel m = new MessagePanel(Communication.builder().getProjectMessageList(0, project.getId()));
        m.setIdProject(project.getId());
        return m;
    }
}
