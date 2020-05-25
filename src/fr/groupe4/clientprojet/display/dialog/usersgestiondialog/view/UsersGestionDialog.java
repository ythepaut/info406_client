package fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller.EventChoixUser;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller.EventGestionUsersConfirm;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller.EventRemoveUser;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceList;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceProject;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceProjectList;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Gestion des utilisateurs
 */
public class UsersGestionDialog extends DrawDialog {
    /**
     * Largeur et hauteur
     */
    private static final int WIDTH = 300, HEIGHT = 200;

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     * Constructeur
     *
     * @param owner   Parent
     * @param project Projet
     */
    public UsersGestionDialog(Window owner, @NotNull Project project) {
        super(owner);
        this.project = project;

        setTitle("Fenêtre de gestions d'utilisateurs au projet");
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
        c.insets = new Insets(10, 5, 10, 5);

        // Récupération de la liste des utilisateurs
        Communication comm = Communication.builder().getHumanResourceList().startNow().sleepUntilFinished().build();
        HumanResourceList users = (HumanResourceList) comm.getResult();

        if (users == null) {
            Logger.error("Users null", c);
            throw new IllegalStateException("Users null");
        }

        // Création du menu d'ajout d'utilisateurs
        JMenuBar menuBarAdd = new JMenuBar();
        menuBarAdd.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        JMenu menu = new JMenu("Ajouter un ou plusieurs utilisateurs");
        menu.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        menuBarAdd.add(menu);

        boolean[] selectedUsers = new boolean[users.size()];

        for (int i = 0; i < users.size(); i++) {
            HumanResource currentUser = users.get(i);
            JCheckBoxMenuItem user = new JCheckBoxMenuItem(
                    currentUser.getFirstname()
                            + " "
                            + currentUser.getLastname());

            selectedUsers[i] = false;
            user.addItemListener(new EventChoixUser(this, currentUser, selectedUsers, i));
            user.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
            user.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
            menu.add(user);
        }

        add(menuBarAdd, c);

        // Récupération de la liste des utilisateurs dans le projet
        comm = Communication.builder().listUsersFromProject(project.getId()).startNow().sleepUntilFinished().build();
        HumanResourceProjectList usersProject = (HumanResourceProjectList) comm.getResult();

        if (usersProject == null) {
            Logger.error("Users null", c);
            throw new IllegalStateException("Users null");
        }

        // Création du menu de suppression d'utilisateur
        JMenuBar barmenusupp = new JMenuBar();
        barmenusupp.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        JMenu menusupp = new JMenu("Supprimer un ou plusieurs utilisateurs");
        menusupp.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        barmenusupp.add(menusupp);

        for (int i = 0; i < usersProject.size(); i++) {
            HumanResource currentUser = usersProject.get(i);
            JCheckBoxMenuItem userremove = new JCheckBoxMenuItem(
                    currentUser.getFirstname()
                            + " "
                            + currentUser.getLastname());

            userremove.addItemListener(new EventRemoveUser(this, currentUser, project));
            userremove.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
            userremove.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
            menusupp.add(userremove);
        }
        c.gridy++;
        add(barmenusupp, c);

        // Création des boutons de confirmation/annulation
        JButton addUsersButton = new JButton("Ajouter les utilisateurs");
        addUsersButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        addUsersButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        addUsersButton.addActionListener(new EventGestionUsersConfirm(this, project, users, selectedUsers));
        c.insets = new Insets(50, 5, 15, 5);
        c.gridwidth = 1;
        c.gridy++;
        add(addUsersButton, c);

        JButton cancelButton = new JButton("Annuler l'ajout");
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        cancelButton.addActionListener(new GenericExitEvent(this));
        c.gridx++;
        add(cancelButton, c);

    }
}
