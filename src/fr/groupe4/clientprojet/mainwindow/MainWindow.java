package fr.groupe4.clientprojet.mainwindow;

import fr.groupe4.clientprojet.mainwindow.enums.MenubarAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {

    private EventMainWindow eventMainWindow;

    public MainWindow(String title) {
        eventMainWindow = new EventMainWindow(this);

        // Définition de la fenêtre
        setTitle(title);
        setMinimumSize(new Dimension(1400, 800));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(eventMainWindow);

        menuBar();


        setVisible(true);
    }


    public void menuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Création du menu Utilisateur
        JMenu userMenu = new JMenu("Utilisateur");
        JMenuItem item = new JMenuItem("Connexion / Deconnexion");
        item.setActionCommand(MenubarAction.CONNECTION.getName());
        item.addActionListener(eventMainWindow);
        userMenu.add(item);
        item = new JMenuItem("Paramètres", 'P');
        item.setActionCommand(MenubarAction.SETTING.getName());
        item.addActionListener(eventMainWindow);
        userMenu.add(item);
        userMenu.addSeparator();
        item = new JMenuItem("Quitter", 'Q');
        item.setActionCommand(MenubarAction.EXIT.getName());
        item.addActionListener(eventMainWindow);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK)); // Raccourcis Ctrl+Q
        userMenu.add(item);

        // Création du menu Tâches
        JMenu taskMenu = new JMenu("Tâches");
        item = new JMenuItem("Créer une tâche");
        item.setActionCommand(MenubarAction.ADDTASK.getName());
        item.addActionListener(eventMainWindow);
        taskMenu.add(item);
        item = new JMenuItem("Supprimer une tâche");
        item.setActionCommand(MenubarAction.DELETETASK.getName());
        item.addActionListener(eventMainWindow);
        taskMenu.add(item);


        // Ajout des menus à la barre de menus
        menuBar.add(userMenu);
        menuBar.add(taskMenu);


        setJMenuBar(menuBar);
    }
}
