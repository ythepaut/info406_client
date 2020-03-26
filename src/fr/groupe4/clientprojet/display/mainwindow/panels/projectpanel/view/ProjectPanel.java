package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.controller.EventLeftPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller.EventProjectPanel;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.display.view.messagepanel.view.MessagePanel;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.project.ProjectList;
import fr.groupe4.clientprojet.model.task.TaskList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Le panel des projets
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
    @Override
    protected void drawContent() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        JLabel title = new JLabel(project.getName());
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        titlePanel.add(title, c);

        add(titlePanel, BorderLayout.NORTH);

        // Les slides
        Slide slides = new Slide();
        slides.addSlide(homePanel(), "HOME");
        slides.addSlide(taskPanel(), "TASK");
        slides.addSlide(messagePanel(), "MESSAGE");

        add(slides, BorderLayout.CENTER);
    }

    /**
     * Dessine le premier slide du projet
     *
     * @return : le jpanel
     */
    private JPanel homePanel() {
        EventProjectPanel eventProjectPanel = new EventProjectPanel(this);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("HOME"), BorderLayout.NORTH);
        panel.add(new JLabel(project.getDescription()), BorderLayout.CENTER);

        //Ajout d'un panel d'ajout de ressources
        JPanel ajoutRessource = new JPanel();
        ajoutRessource.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));
        ajoutRessource.setLayout(new FlowLayout());
        JButton bouttonAddRessourcesMateriel = new JButton("Ajouter une ressources matériels au projet");
        JButton bouttonAddRessourcesHumaine = new JButton ("Ajouter un membre au projet");
        ajoutRessource.add(bouttonAddRessourcesHumaine);
        ajoutRessource.add(bouttonAddRessourcesMateriel);
        panel.add(ajoutRessource,BorderLayout.SOUTH);

        //Création du controller de ressources humaines
        bouttonAddRessourcesHumaine.setActionCommand(EventProjectPanel.NEWUSERS);
        bouttonAddRessourcesHumaine.addActionListener(eventProjectPanel);

        //Création du controller de ressources matériels
        bouttonAddRessourcesMateriel.setActionCommand(EventProjectPanel.NEWMAT);
        bouttonAddRessourcesMateriel.addActionListener(eventProjectPanel);


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
        Communication c = Communication.builder().sleepUntilFinished().startNow().getTaskList(project.getId()).build();
        TaskList tasks = (TaskList) c.getResult();
        panel.setLayout(new GridLayout());
        JPanel panelL = new JPanel();
        JPanel panelC = new JPanel();
        JPanel panelR = new JPanel();
        panel.add(panelL);
        panel.add(panelC);
        panel.add(panelR);
        panelL.setLayout(new BoxLayout(panelL, BoxLayout.Y_AXIS));
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));
        panelR.setLayout(new BoxLayout(panelR, BoxLayout.Y_AXIS));
        panelL.add(new JLabel("TÂCHES"));
        panelC.add(new JLabel("DESCRIPTION"));
        panelR.add(new JLabel("DATE LIMITE"));
        assert tasks != null;
        for (fr.groupe4.clientprojet.model.task.Task task : tasks) {
            panelL.add(new JLabel(task.getName()));
            panelC.add(new JLabel(task.getDescription()));
            if (task.getDeadline() != null) {
                panelR.add(new JLabel(task.getDeadline().toString()));
            }
        }
        return panel;
    }

    /**
     * Dessine le slide de la messagerie du projet
     *
     * @return : le jpanel
     */
    private JPanel messagePanel() {
        MessagePanel m = new MessagePanel(Communication.builder().getProjectMessageList(0, project.getId()));
        m.setIdProject(project.getId());
        return m;
    }
}
