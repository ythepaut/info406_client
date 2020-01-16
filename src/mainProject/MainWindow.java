package mainProject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        setMinimumSize(new Dimension(600, 400)); // Taille minimum de la fenêtre
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        }); // Event qui gère la fermeture de la fenêtre


        // Création de la barre de menu
        Menu userMenu = new Menu("Utilisateur");
        MenuItem connectionMenuItem = new MenuItem("Connexion / Déconnexion");
        connectionMenuItem.setActionCommand("connection");

        MenuItem exitMenuItem = new MenuItem("Quitter");
        exitMenuItem.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
        exitMenuItem.setActionCommand("exit");

        userMenu.add(connectionMenuItem);
        userMenu.add(exitMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.add(userMenu);
        setMenuBar(menuBar);



        setVisible(true);
    }
}
