package fr.groupe4.clientprojet.display.dialog.exitdialog.view;

import fr.groupe4.clientprojet.display.dialog.exitdialog.controller.EventExitDialog;
import fr.groupe4.clientprojet.display.dialog.exitdialog.controller.KeyEventExitDialog;
import fr.groupe4.clientprojet.display.dialog.exitdialog.enums.ExitChoice;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre de confirmation pour la sortie du logiciel
 */
public class ExitDialog extends DrawDialog {
    /**
     * La frame qui appelle ce dialog
     */
    private Window owner;
    /**
     * Le listener du dialog
     */
    private EventExitDialog eventExitDialog;

    /**
     * Le constructeur
     *
     * @param owner : la frame à laquelle appartient le dialog
     */
    public ExitDialog(Window owner, String avertissement) {
        setTitle(avertissement);
        this.owner = owner;
        setSize(300, 70);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setModal(true);
        eventExitDialog = new EventExitDialog(this); // Le listener du dialog

        drawContent();

        setVisible(true);
    }

    /**
     * Dessine le contenu du dialog
     */
    @Override
    protected void drawContent() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));

        JButton exitButton = new JButton("Quitter");
        exitButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        exitButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        exitButton.requestFocus();
        exitButton.setActionCommand(ExitChoice.EXIT.getName());
        exitButton.addActionListener(eventExitDialog);
        exitButton.addKeyListener(new KeyEventExitDialog(this));
        panel.add(exitButton);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        cancelButton.setActionCommand(ExitChoice.CANCEL.getName());
        cancelButton.addActionListener(eventExitDialog);
        panel.add(cancelButton);

        add(panel);
    }

    /**
     * Renvoie la frame qui a appelé ce dialog
     *
     * @return : la frame qui a appelé ce dialog
     */
    @Override
    public Window getOwner() {
        return owner;
    }
}
