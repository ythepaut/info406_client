package fr.groupe4.clientprojet.display.dialog.parametersdialog.view;

import fr.groupe4.clientprojet.display.dialog.parametersdialog.controller.EventParametersDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * Le dialog des paramètres
 */
public class ParametersDialog extends DrawDialog {
    /**
     * Le listener du dialog
     */
    private EventParametersDialog eventParametersDialog;
    /**
     * Le panel qui est déssiné au centre
     */
    private ParametersPanel centerPanel;
    /**
     * L'arbre de sélection
     */
    private JTree tree;

    /**
     * Le constructeur
     */
    public ParametersDialog() {
        setTitle("Paramètres");
        setSize(1000, 600);
        setModal(true);
        eventParametersDialog = new EventParametersDialog(this);
        centerPanel = generalPanel(); // On dessine le premier noeud de base
        addWindowListener(eventParametersDialog);

        drawContent();

        setVisible(true);
    }

    /**
     * Dessine le contenu
     */
    @Override
    protected void drawContent() {
        setLayout(new BorderLayout());

        // Construction de l'arbre
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        createNodes(root);
        tree = new JTree(root);
        tree.setRootVisible(false);
        tree.addTreeSelectionListener(eventParametersDialog);

        add(tree, BorderLayout.WEST);

        // On déssine le panel au centre
        add(centerPanel, BorderLayout.CENTER);
    }

    public JTree getTree() {
        return tree;
    }

    public void setCenterPanel(ParametersPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    /**
     * Contruit l'arbre
     *
     * @param root La racine de l'arbre
     */
    private void createNodes(DefaultMutableTreeNode root) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Géneral"); // Catégorie générale

        // On utilise toujours la même variable pour ne pas avoir à en redéfinir à chaque fois
        DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(generalPanel());
        category.add(leaf);
        leaf = new DefaultMutableTreeNode(appearancePanel());
        category.add(leaf);

        root.add(category);

    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.
    // Tout les panels qui peuvent être déssinés

    private ParametersPanel generalPanel() {
        ParametersPanel panel = new ParametersPanel("Général");
        panel.add(new JLabel("Général"));

        return panel;
    }

    private ParametersPanel appearancePanel() {
        ParametersPanel panel = new ParametersPanel("Apparence");
        panel.add(new JLabel("Apparence"));

        return panel;
    }
}
