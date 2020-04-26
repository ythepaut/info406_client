package fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.controller.EventTaskCreation;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.time.DayOfWeek;

/**
 * Dialog de création de tâche
 */
public class TaskCreationDialog extends DrawDialog {
    /**
     * Largeur
     */
    private static final int WIDTH = 300;

    /**
     * Hauteur
     */
    private static final int HEIGHT = 550;

    /**
     * Nombre de lignes pour la description
     */
    private static final int DESCRIPTION_NB_ROWS = 18;

    /**
     * Nombre en colonnes pour la description
     */
    private static final int DESCRIPTION_NB_COLS = 22;

    /**
     * Nombre de colonnes pour le nom
     */
    private static final int NAME_NB_COLS = 15;

    /**
     * Parent
     */
    @NotNull
    private final JPanel parent;

    /**
     * Projet associé
     */
    @NotNull
    private final Project project;

    /**
     * Constructeur
     *
     * @param parent Parent
     * @param project Projet associé
     */
    public TaskCreationDialog(@NotNull JPanel parent, @NotNull Project project) {
        this.parent = parent;
        this.project = project;

        setTitle("Fenêtre de création de tâche");
        setModal(true);

        drawContent();
        setVisible(true);
    }

    /**
     * Affichage
     */
    @Override
    protected void drawContent() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        GridBagConstraints c = new GridBagConstraints();

        setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5,0,5,0);

        add(new JLabel("Entrez le nom de la tâche : "), c);

        JTextField projectName = new JTextField(NAME_NB_COLS);
        c.gridy++;
        add(projectName, c);

        c.gridy++;
        add(new JLabel("Entrez la description de la tâche : "), c);

        JTextArea description = new JTextArea(DESCRIPTION_NB_ROWS, DESCRIPTION_NB_COLS);
        description.setLineWrap(true);
        c.gridy++;
        add(description,c);

         // Entrée de la date limite du projet
        c.gridy++;
        add(new JLabel("Entrez la date limite de la tâche : "), c);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(dateSettings);
        c.gridy++;
        add(datePicker,c);

        // Création des boutons de confirmation/annulation
        JButton createProjectButton = new JButton("Création Tâche");
        createProjectButton.addActionListener(new EventTaskCreation(
                this,
                datePicker,
                projectName,
                description,
                project));

        c.gridwidth = 1;
        c.gridy++;
        add(createProjectButton, c);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new GenericExitEvent(this));
        c.gridx++;
        add(cancelButton,c);
    }
}
