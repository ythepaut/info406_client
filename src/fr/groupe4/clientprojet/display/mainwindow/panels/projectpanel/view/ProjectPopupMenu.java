package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view;

import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller.PrintProjectListener;

import javax.swing.*;

public class ProjectPopupMenu extends JPopupMenu {
    public ProjectPopupMenu(JPanel p, JComponent importantComponent) {
        JMenuItem printPage = new JMenuItem("Impression");

        printPage.addActionListener(new PrintProjectListener(importantComponent));

        add(printPage);
    }
}
