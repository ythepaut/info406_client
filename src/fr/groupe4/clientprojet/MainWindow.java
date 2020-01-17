package fr.groupe4.clientprojet;

import java.awt.*;
import java.awt.event.*;

public class MainWindow extends Frame {
    private EventMainWindow eventMainWindow;

    /**
     * Constructeur de la fenêtre principale
     */
    public MainWindow() {
        eventMainWindow = new EventMainWindow(this); // Pour les events

        // Définition de la fenêtre
        setTitle("Team's Project"); // TODO: Il faut trouver un nom pour le logiciel
        setMinimumSize(new Dimension(1400, 800)); // Taille minimum de la fenêtre
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran


        addWindowListener(eventMainWindow);


        menuBar(); // Barre de menu


        setLayout(new BorderLayout()); // Permet de faire plus simplements des panneaux sur les côtés (EAST et WEST)



        setVisible(true);
    }

    /**
     * Créé la barre de menu
     */
    public void menuBar() {
        Menu userMenu = new Menu("Utilisateur"); // Menu utilisateur de la barre de menu

        MenuItem connectionMenuItem = new MenuItem("Connexion / Déconnexion");
        connectionMenuItem.setActionCommand(EventMainWindow.CONNECTION);
        MenuItem exitMenuItem = new MenuItem("Quitter");
        exitMenuItem.setShortcut(new MenuShortcut(KeyEvent.VK_Q)); // Ctrl+Q
        exitMenuItem.setActionCommand(EventMainWindow.EXIT);

        connectionMenuItem.addActionListener(eventMainWindow);
        exitMenuItem.addActionListener(eventMainWindow); // Création des listener des items
        userMenu.add(connectionMenuItem);
        userMenu.add(exitMenuItem); // Ajout des items au menu


        Menu taskMenu = new Menu("Tâches"); // Menu tâches de la barre de menu

        MenuItem addTaskMenuItem = new MenuItem("Ajouter une tâche");
        addTaskMenuItem.setActionCommand(EventMainWindow.ADDTASK);
        MenuItem deleteTaskMenuItem = new MenuItem("Supprimer une tâche");
        deleteTaskMenuItem.setActionCommand(EventMainWindow.DELETETASK);

        addTaskMenuItem.addActionListener(eventMainWindow);
        deleteTaskMenuItem.addActionListener(eventMainWindow); // Création des listener des items
        taskMenu.add(addTaskMenuItem);
        taskMenu.add(deleteTaskMenuItem); // Ajout des items au menu


        MenuBar menuBar = new MenuBar(); // Création de la barre de menu
        menuBar.add(userMenu);
        menuBar.add(taskMenu); // Ajout des menu à la barre de menu
        setMenuBar(menuBar); // Ajout de la barre de menu à la fenêtre
    }
}