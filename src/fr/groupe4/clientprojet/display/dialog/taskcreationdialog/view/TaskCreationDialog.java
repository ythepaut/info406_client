package fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.controller.EventTaskCreation;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceProjectList;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
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
     * Projet associé
     */
    @NotNull
    private final Project project;

    /**
     * Constructeur
     *
     * @param owner   Parent
     * @param project Projet associé
     */
    public TaskCreationDialog(Window owner, @NotNull Project project) {
        super(owner);
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
        getContentPane().setBackground(Theme.FOND.getColor(Parameters.getThemeName()));

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);

        GridBagConstraints c = new GridBagConstraints();

        setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5, 0, 5, 0);
        JLabel nt = new JLabel("Entrez le nom de la tâche : ");
        nt.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(nt, c);

        JTextField projectName = new JTextField(NAME_NB_COLS);
        c.gridy++;
        projectName.setBorder(null);
        projectName.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        projectName.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(projectName, c);

        c.gridy++;
        JLabel dt = new JLabel("Entrez la description de la tâche : ");
        dt.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(dt, c);

        JTextArea description = new JTextArea(DESCRIPTION_NB_ROWS, DESCRIPTION_NB_COLS);
        description.setLineWrap(true);
        c.gridy++;
        description.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        description.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(description, c);

        // Entrée de la date limite du projet
        c.gridy++;
        JLabel dlt = new JLabel("Entrez la date limite de la tâche : ");
        dlt.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(dlt, c);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(dateSettings);
        c.gridy++;
        datePicker.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        datePicker.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(datePicker, c);

        //utilisateurs concernés
        // Récupération de la liste des utilisateurs
        Communication comm = Communication.builder().listUsersFromProject(project.getId()).startNow().sleepUntilFinished().build();
        HumanResourceProjectList users = (HumanResourceProjectList) comm.getResult();

        if (users == null) {
            Logger.error("Users null", c);
            throw new IllegalStateException("Users null");
        }

        c.gridy++;
        JMenuBar menuBarAdd = new JMenuBar();
        menuBarAdd.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));

        JMenu menu = new JMenu("utilisateurs concernés");
        menu.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        menuBarAdd.add(menu);


        for (int i = 0; i < users.size(); i++) {
            JMenuItem user = new JMenuItem(users.get(i).getFirstname() + " " + users.get(i).getLastname());
            user.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
            user.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
            menu.add(user);
        }

        add(menuBarAdd, c);

        // Création des boutons de confirmation/annulation
        JButton createProjectButton = new JButton("Création Tâche");
        createProjectButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        createProjectButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
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
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        cancelButton.addActionListener(new GenericExitEvent(this));
        c.gridx++;
        add(cancelButton, c);
    }
}
