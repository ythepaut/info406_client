package fr.groupe4.clientprojet.display.dialog.projectcreationdialog.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.projectcreationdialog.controller.EventProjectCreation;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.DayOfWeek;

/**
 * Fenêtre de création de projet
 */
public class ProjectCreationDialog extends DrawDialog {
    /**
     * Largeur initiale
     */
    private static final int WIDTH = 300;

    /**
     * Hauteur initiale
     */
    private static final int HEIGHT = 550;

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
     * @param owner Propriétaire
     */
    public ProjectCreationDialog(@NotNull JFrame owner) {
        super(owner);
        setTitle("Fenêtre de création de Projet");
        setModal(true);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Theme.BORDER.getColor(Parameters.getThemeName())));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);

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

        //Déclaration du layout
        setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5, 0, 5, 0);

        // Entrée du nom
        JLabel labelnom = new JLabel(("Entrez le nom du projet : "));
        labelnom.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(labelnom, c);

        JTextField projectNameTextField = new JTextField(NB_COLS_NAME);
        projectNameTextField.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        projectNameTextField.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        projectNameTextField.setBorder(null);
        c.gridy++;
        add(projectNameTextField, c);

        // Description de projet
        c.gridy++;
        JLabel labeldescription = new JLabel(("Entrez la description du projet : "));
        labeldescription.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(labeldescription, c);

        JTextArea descriptionTextArea = new JTextArea(NB_ROWS, NB_COLS);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        descriptionTextArea.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        c.gridy++;
        add(descriptionTextArea, c);

        // Entrée de la date limite du projet
        c.gridy++;
        JLabel labeldate = new JLabel("Entrez la date limite du projet : ");
        labeldate.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(labeldate, c);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(dateSettings);
        c.gridy++;
        add(datePicker, c);

        // Création des boutons de confirmation/annulation
        JButton createProjectButton = new JButton("Création Projet");
        createProjectButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        createProjectButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        createProjectButton.addActionListener(new EventProjectCreation(
                this,
                datePicker,
                projectNameTextField,
                descriptionTextArea));

        c.gridwidth = 1;
        c.gridy++;
        add(createProjectButton, c);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        cancelButton.addActionListener(new GenericExitEvent(this));
        c.gridx++;
        add(cancelButton, c);

    }
}
