package fr.groupe4.clientprojet.display.dialog.usersadddialog.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.usersadddialog.controller.EventChoixUser;
import fr.groupe4.clientprojet.display.dialog.usersadddialog.controller.EventExitGestionUsers;
import fr.groupe4.clientprojet.display.dialog.usersadddialog.controller.EventGestionUsersConfirm;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPanel;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceList;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UsersAddDialog extends DrawDialog {

    /**
     * Declaration des variables necessaires
     */
    private GridBagConstraints c;

    /**
     * La frame qui appelle ce dialog
     */
    private ProjectPanel owner;

    /**
     * Id du projet
     */
    private long projectId;

    /**
     * Constructeur
     * @param owner
     */
    public UsersAddDialog(ProjectPanel owner, long projectId) {
        this.projectId = projectId;

        c = new GridBagConstraints();

        setTitle("Fenêtre de gestions d'utilisateurs au projet");
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

        setSize(350, 400);
        setResizable(false);

        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
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
        c.insets = new Insets(5,5,5,5);

        //Récupération de la liste des utilisateurs
        Communication com = Communication.builder().getHumanResourceList().startNow().sleepUntilFinished().build();
        HumanResourceList listeusers = (HumanResourceList) com.getResult();

        /**
         * Création du menu d'ajout d'utilisateurs
         */
        JMenuBar barmenuajout = new JMenuBar();
        JMenu menuajout = new JMenu("Ajouter un ou plusieurs utilisateurs");
        barmenuajout.add(menuajout);
        boolean userchoisis[] = new boolean[listeusers.size()];
        for (int i = 0; i<listeusers.size(); i++){
            HumanResource usercourant = listeusers.get(i);
            String prenomuser = usercourant.getFirstname();
            String nomuser = usercourant.getLastname();
            JCheckBoxMenuItem user = new JCheckBoxMenuItem(prenomuser + " " + nomuser);
            userchoisis[i] = false;
            user.addItemListener(new EventChoixUser(this, usercourant, userchoisis, i));
            menuajout.add(user);
        }
        add(barmenuajout,c);


        /**
         * Création du menu de suppression d'utilisateurs
         */
        c.gridy++;
        JMenuBar barmenusupp = new JMenuBar();
        JMenu menusupp = new JMenu("Supprimer un ou plusieurs utilisateurs");
        barmenusupp.add(menusupp);
        JCheckBoxMenuItem user5 = new JCheckBoxMenuItem("user5");
        menusupp.add(user5);
        JCheckBoxMenuItem user6 = new JCheckBoxMenuItem("user6");
        menusupp.add(user6);
        JCheckBoxMenuItem user7 = new JCheckBoxMenuItem("user7");
        menusupp.add(user7);
        JCheckBoxMenuItem user8 = new JCheckBoxMenuItem("user8");
        menusupp.add(user8);
        add(barmenusupp,c);


        /**
         * Création des boutons de confirmation/annulation
         */
        c.gridwidth = 1;
        c.gridy++;
        JButton ajouterusers = new JButton("Ajouter les utilisateurs");
        add(ajouterusers,c);
        ajouterusers.addActionListener(new EventGestionUsersConfirm(this, projectId, listeusers, userchoisis));
        c.gridx++;
        JButton cancelButton = new JButton("Annuler l'ajout");
        add(cancelButton,c);
        cancelButton.addActionListener(new EventExitGestionUsers(this));
    }
}
