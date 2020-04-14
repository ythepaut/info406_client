package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view;

import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPanel;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;

import javax.swing.*;
import java.awt.*;

public class MaterielGestionDialog extends DrawDialog {


    /**
     * La frame qui appelle ce dialog
     */
    private ProjectPanel owner;

    private GridBagConstraints c;

    /**
     * Constructeur
     * @param owner
     */
    public MaterielGestionDialog(ProjectPanel owner) {
        c = new GridBagConstraints();

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

        setSize(400, 550);
        setResizable(false);
        //Enlever les deux // pour faire une belle fenêtre sans la croix (bordure noire autour)
        //   setUndecorated(true);
        //   rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);



        /**
         * Déclaration du layout
         */
        this.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5,0,5,0);


        c.gridwidth = 1;
        c.gridy++;
        JButton ajouterusers = new JButton("Ajouter les utilisateurs");
        //   ajouterusers.addActionListener(new ());
        add(ajouterusers,c);
        c.gridx++;
        JButton cancelButton = new JButton("Annuler l'ajout");
        //   cancelButton.addActionListener(new ());
        add(cancelButton,c);
    }
}
