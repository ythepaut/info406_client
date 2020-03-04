package fr.groupe4.clientprojet.display.dialog.exitdialog.view;

import fr.groupe4.clientprojet.display.dialog.exitdialog.controller.EventExitDialog;
import fr.groupe4.clientprojet.display.dialog.exitdialog.enums.ExitChoice;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre de confirmation pour la sortie du logiciel
 */
public class ExitDialog extends DrawDialog {
    /**
     * Le listener du dialog
     */
    private EventExitDialog eventExitDialog;
    /**
     * La frame qui appelle ce dialog
     */
    private JFrame owner;

    /**
     * Le constructeur
     *
     * @param owner : la frame à laquelle appartient le dialog
     */
    public ExitDialog(JFrame owner) {
        setTitle("Êtes-vous sûr de vouloir quitter ?");
        this.owner = owner;
        setModal(true);

        drawContent();

        setVisible(true);
    }

    /**
     * Dessine le contenu du dialog
     */
    @Override
    protected void drawContent() {
        eventExitDialog = new EventExitDialog(this);

        setSize(300, 70);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        setContentPane(new JPanel());


        JButton exitButton = new JButton("Quitter");
        exitButton.setActionCommand(ExitChoice.EXIT.getName());
        exitButton.addActionListener(eventExitDialog);
        getContentPane().add(exitButton);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setActionCommand(ExitChoice.CANCEL.getName());
        cancelButton.addActionListener(eventExitDialog);
        getContentPane().add(cancelButton);
    }

    /**
     * Renvoie la frame qui a appelé ce dialog
     *
     * @return : la frame qui a appelé ce dialog
     */
    @Override
    public JFrame getOwner() {
        return owner;
    }
}
