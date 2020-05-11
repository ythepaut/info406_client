package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view;

import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller.EventProjectPanel;
import fr.groupe4.clientprojet.model.project.Project;

import javax.swing.*;
import java.awt.*;

public class HomeProjectPanel extends JPanel {
    public HomeProjectPanel(Project project) {
        super(new BorderLayout());

        EventProjectPanel eventProjectPanel = new EventProjectPanel(project);

        add(new JLabel("HOME"), BorderLayout.NORTH);
        add(new JLabel(project.getDescription()), BorderLayout.CENTER);

        //Ajout d'un panel d'ajout de ressources
        JPanel ajoutRessource = new JPanel();
        ajoutRessource.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        ajoutRessource.setLayout(new FlowLayout());
        JButton bouttonAddRessourcesMateriel = new JButton("Gérer les ressources matériels du projet");
        JButton bouttonAddRessourcesHumaine = new JButton("Gérer les utilisateurs du projet");
        ajoutRessource.add(bouttonAddRessourcesHumaine);
        ajoutRessource.add(bouttonAddRessourcesMateriel);

        add(ajoutRessource, BorderLayout.SOUTH);

        //Création du controller de ressources humaines
        bouttonAddRessourcesHumaine.setActionCommand(EventProjectPanel.NEWUSERS);
        bouttonAddRessourcesHumaine.addActionListener(eventProjectPanel);

        //Création du controller de ressources matériels
        bouttonAddRessourcesMateriel.setActionCommand(EventProjectPanel.NEWMAT);
        bouttonAddRessourcesMateriel.addActionListener(eventProjectPanel);
    }
}
