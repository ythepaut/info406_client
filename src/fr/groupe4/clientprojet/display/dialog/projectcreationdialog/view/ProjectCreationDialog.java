package fr.groupe4.clientprojet.display.dialog.projectcreationdialog.view;

import fr.groupe4.clientprojet.display.dialog.exitdialog.controller.EventExitDialog;
import fr.groupe4.clientprojet.display.dialog.exitdialog.controller.KeyEventExitDialog;
import fr.groupe4.clientprojet.display.dialog.exitdialog.enums.ExitChoice;
import fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.view.LeftPanel;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Fenetre de création de projet
 */
public class ProjectCreationDialog extends DrawDialog {


    /**
     * La frame qui appelle ce dialog
     */
    private JFrame owner;

    /**
     * Constructeur
     * @param owner
     */
    public ProjectCreationDialog(JFrame owner) {
        setTitle("Fenetre de création de Projet");
        this.owner = owner;
        setModal(true);

        drawContent();
        setVisible(true);
    }

    /**
     * Dessine le contenu du dialog de création
     */
    @Override
    protected void drawContent() {

        setSize(300, 700);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        setContentPane(new JPanel());


        JButton exitButton = new JButton("Quitter");
        exitButton.requestFocus();
        getContentPane().add(exitButton);
        JButton cancelButton = new JButton("Annuler");
        getContentPane().add(cancelButton);
    }
}
