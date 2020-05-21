package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view;

import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Gestion du materiel
 */
public class MaterielGestionDialog extends DrawDialog {
    /**
     * Largeur initiale
     */
    private static final int WIDTH = 400;

    /**
     * Hauteur initiale
     */
    private static final int HEIGHT = 550;

    /**
     * Constructeur
     *
     * @param owner Propriétaire
     */
    public MaterielGestionDialog(Window owner) {
        super(owner);
        setTitle("Fenêtre de création de Projet");
        setModal(true);
        this.setBackground(Theme.FOND.getColor());

        drawContent();
        setVisible(true);
    }

    /**
     * Dessine le contenu du dialog de création
     */
    @Override
    protected void drawContent() {
        getContentPane().setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        GridBagConstraints c = new GridBagConstraints();

        setSize(WIDTH, HEIGHT);
        setResizable(false);

        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);

        // Déclaration du layout
        setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5, 0, 5, 0);

        c.gridwidth = 1;
        c.gridy++;
        JButton addUsersButton = new JButton("Ajouter les utilisateurs");
        // TODO
        //   ajouterusers.addActionListener(new ());
        add(addUsersButton, c);
        addUsersButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        addUsersButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        c.gridx++;
        JButton cancelButton = new JButton("Annuler l'ajout");
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        //   cancelButton.addActionListener(new ());
        add(cancelButton, c);
    }
}
