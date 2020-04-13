package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view;

import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller.PrintProjectListener;

import javax.swing.*;

/**
 * Menu contextuel de clic droit
 */
public class ProjectPopupMenu extends JPopupMenu {
    /**
     * Constructeur
     *
     * @param p Panel parent
     */
    public ProjectPopupMenu(JPanel p) {
        JMenuItem printPage = new JMenuItem("Impression");

        printPage.addActionListener(new PrintProjectListener(p));

        add(printPage);
    }
}
