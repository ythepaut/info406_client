package fr.groupe4.clientprojet;

import java.awt.*;
import java.awt.event.*;

public class MainWindow extends Frame {

    public static void main(String[] args) {
        new MainWindow();
    }

    /**
     * Constructeur de la fenêtre principale
     */
    public MainWindow() {
        // Définition de la fenêtre
        setTitle("Groupe 4"); // TODO: Il faut trouver un nom pour le logiciel
        setMinimumSize(new Dimension(1400, 800)); // Taille minimum de la fenêtre
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitDialog(e);
            }
        }); // Event qui gère la fermeture de la fenêtre



        // Création de la barre de menu
        Menu userMenu = new Menu("Utilisateur");

        MenuItem connectionMenuItem = new MenuItem("Connexion / Déconnexion");
        connectionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO: Créer la fenêtre de connexion
            }
        });

        MenuItem exitMenuItem = new MenuItem("Quitter");
        exitMenuItem.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Fermer la fenêtre principale proprement

                System.exit(0);
            }
        });

        userMenu.add(connectionMenuItem);
        userMenu.add(exitMenuItem);

        Menu taskMenu = new Menu("Tâches");

        MenuItem addTaskMenuItem = new MenuItem("Ajouter une tâche");
        addTaskMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO: Créer la fenêtre d'ajout de tâches
            }
        });

        MenuItem deleteTaskMenuItem = new MenuItem("Supprimer une tâche");
        deleteTaskMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO: Créer la fenêtre de suppression de tâches
            }
        });

        taskMenu.add(addTaskMenuItem);
        taskMenu.add(deleteTaskMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.add(userMenu);
        menuBar.add(taskMenu);
        setMenuBar(menuBar);



        setVisible(true);
    }

    /**
     * Affiche une fenêtre de dialogue pour confirmer l'arret du logiciel
     *
     * @param e L'event sur la fenêtre
     */
    public void exitDialog(WindowEvent e) {
        Dialog d = new Dialog(e.getWindow(), "Êtes-vous sur de quitter ?");
        d.setSize(300, 70);
        d.setLocationRelativeTo(null);
        GridLayout layout = new GridLayout(1, 2);
        d.setLayout(layout);
        Button exitButton = new Button("Quitter");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Fermer la fenêtre principale proprement
                System.exit(0);
            }
        });
        d.add(exitButton);
        Button cancelButton = new Button("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                d.dispose();
            }
        });
        d.add(cancelButton);
        d.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });

        d.setVisible(true);
    }
}