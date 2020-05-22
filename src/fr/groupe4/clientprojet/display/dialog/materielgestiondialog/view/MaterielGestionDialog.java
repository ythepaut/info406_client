package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller.EventChoixMats;
import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller.EventGestionMaterialConfirm;
import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller.EventRemoveMaterial;
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
import fr.groupe4.clientprojet.model.resource.human.HumanResourceProjectList;
import fr.groupe4.clientprojet.model.resource.material.MaterialResource;
import fr.groupe4.clientprojet.model.resource.material.MaterialResourceList;
import fr.groupe4.clientprojet.model.resource.material.MaterialResourceProjectList;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Gestion du materiel
 */
public class MaterielGestionDialog extends DrawDialog {
    /**
     * Largeur et hauteur
     */
    private static final int WIDTH = 300, HEIGHT = 250;

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     * Constructeur
     *
     * @param owner Propriétaire
     * @param project Projet
     */
    public MaterielGestionDialog(Window owner, Project project) {
        super(owner);
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
        c.insets = new Insets(10, 5, 10, 5);


        //TODO : Creer sa propre ressource matériel
        JButton creerMat = new JButton("Creer votre ressources");
        creerMat.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        creerMat.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        add(creerMat,c);

        // Récupération de la liste des matériels
        Communication comm = Communication.builder().listMaterialResource().startNow().sleepUntilFinished().build();
        MaterialResourceList mats = (MaterialResourceList) comm.getResult();

        if (mats == null) {
            Logger.error("Matériels null", c);
            throw new IllegalStateException("Matériels null");
        }

        // Création du menu d'ajout d'utilisateurs
        JMenuBar menuBarAdd = new JMenuBar();
        menuBarAdd.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        JMenu menu = new JMenu("Ajouter une ou plusieurs ressources");
        menu.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        menuBarAdd.add(menu);

        boolean[] selectedMats = new boolean[mats.size()];

        for (int i = 0; i < mats.size(); i++) {
            MaterialResource currentMat = mats.get(i);
            JCheckBoxMenuItem mat = new JCheckBoxMenuItem(currentMat.getName());
            selectedMats[i] = false;
            mat.addItemListener(new EventChoixMats(this, currentMat, selectedMats, i));
            mat.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
            mat.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
            menu.add(mat);
        }
        c.gridy++;
        add(menuBarAdd, c);

        // Récupération de la liste des matériels dans le projet
        comm = Communication.builder().listMaterialFromProject(project.getId()).startNow().sleepUntilFinished().build();
        MaterialResourceProjectList materialProject = (MaterialResourceProjectList) comm.getResult();

        if (materialProject == null) {
            Logger.error("Matériel null", c);
            throw new IllegalStateException("Matériel null");
        }

        // Création du menu de suppression d'utilisateur
        JMenuBar barmenusupp = new JMenuBar();
        barmenusupp.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        JMenu menusupp = new JMenu("Supprimer une ou plusieurs ressources");
        menusupp.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        barmenusupp.add(menusupp);
        System.out.println(materialProject.size());

        for (int i = 0; i < materialProject.size(); i++) {
            MaterialResource currentMat = materialProject.get(i);
            JCheckBoxMenuItem matremove = new JCheckBoxMenuItem(currentMat.getName());
            matremove.addItemListener(new EventRemoveMaterial(this, currentMat, project));
            matremove.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
            matremove.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
            menusupp.add(matremove);
        }
        c.gridy++;
        add(barmenusupp, c);

        // Création des boutons de confirmation/annulation
        JButton addMatButton = new JButton("Ajouter les ressources");
        addMatButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        addMatButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        addMatButton.addActionListener(new EventGestionMaterialConfirm(this, project, mats, selectedMats));
        c.insets = new Insets(50, 5, 15, 5);
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
