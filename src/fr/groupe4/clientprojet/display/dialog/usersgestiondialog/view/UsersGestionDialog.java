package fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller.EventChoixUser;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller.EventGestionUsersConfirm;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceList;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

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

        drawContent();
        setVisible(true);
    }


    /**
     * Dessine le contenu du dialog de création
     */
    @Override
    protected void drawContent() {
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
        JMenu menu = new JMenu("Ajouter un ou plusieurs utilisateurs");
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
            menu.add(user);
        }

        add(menuBarAdd, c);

        // Création du menu de suppression d'utilisateur
        // TODO
        JMenuBar barmenusupp = new JMenuBar();
        JMenu menusupp = new JMenu("Supprimer un ou plusieurs utilisateurs");
        barmenusupp.add(menusupp);
        JCheckBoxMenuItem user5 = new JCheckBoxMenuItem("user1");
        menusupp.add(user5);
        JCheckBoxMenuItem user6 = new JCheckBoxMenuItem("user2");
        menusupp.add(user6);
        JCheckBoxMenuItem user7 = new JCheckBoxMenuItem("user3");
        menusupp.add(user7);
        JCheckBoxMenuItem user8 = new JCheckBoxMenuItem("user4");
        menusupp.add(user8);

        c.gridy++;
        add(barmenusupp, c);

        // Création des boutons de confirmation/annulation
        JButton addUsersButton = new JButton("Ajouter les utilisateurs");
        addUsersButton.addActionListener(new EventGestionUsersConfirm(this, project, users, selectedUsers));
        c.insets = new Insets(50, 5, 15, 5);
        c.gridwidth = 1;
        c.gridy++;
        add(addUsersButton, c);

        JButton cancelButton = new JButton("Annuler l'ajout");
        cancelButton.addActionListener(new GenericExitEvent(this));
        c.gridx++;
        add(cancelButton, c);

    }
}
