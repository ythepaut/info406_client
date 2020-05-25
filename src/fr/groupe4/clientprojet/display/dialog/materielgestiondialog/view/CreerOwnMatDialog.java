package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view;

import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller.EventCreerOwnMatConfirm;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class CreerOwnMatDialog extends DrawDialog {
    /**
     * Largeur et hauteur
     */
    private static final int WIDTH = 300, HEIGHT = 450;

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     * Nombre de lignes pour la description du projet
     */
    private static final int NB_ROWS = 18;

    /**
     * Nombre de colonnes pour la description du projet
     */
    private static final int NB_COLS = 22;

    /**
     * Nombre de colonnes pour le nom
     */
    private static final int NB_COLS_NAME = 15;

    /**
     * Constructeur
     *
     * @param source Propriétaire
     * @param project Projet
     */
    public CreerOwnMatDialog(@NotNull DrawDialog source, @NotNull Project project) {
        super(source);
        this.project = project;

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

        //Entrée du nom de la ressource
        JLabel labelnom = new JLabel("Nom de la ressource");
        labelnom.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        c.gridy++;
        add(labelnom, c);

        JTextField ressourceNameTextField = new JTextField(NB_COLS_NAME);
        ressourceNameTextField.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        ressourceNameTextField.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        ressourceNameTextField.setBorder(null);
        c.gridy++;
        add(ressourceNameTextField, c);

        //Entrée de la description de la ressource
        JLabel labeldescription = new JLabel("Description de la ressource");
        labeldescription.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        c.gridy++;
        add(labeldescription, c);

        JTextArea ressourceDesTextArea = new JTextArea(NB_ROWS, NB_COLS);
        ressourceDesTextArea.setLineWrap(true);
        ressourceDesTextArea.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        ressourceDesTextArea.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        c.gridy++;
        add(ressourceDesTextArea, c);


        // Création des boutons de confirmation/annulation
        JButton addMatButton = new JButton("Ajouter les ressources");
        addMatButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        addMatButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        addMatButton.addActionListener(new EventCreerOwnMatConfirm(this, project, ressourceNameTextField, ressourceDesTextArea));
        c.gridwidth = 1;
        c.gridy++;
        add(addMatButton, c);

        JButton cancelButton = new JButton("Annuler l'ajout");
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        cancelButton.addActionListener(new GenericExitEvent(this));
        c.gridx++;
        add(cancelButton, c);
    }
}
