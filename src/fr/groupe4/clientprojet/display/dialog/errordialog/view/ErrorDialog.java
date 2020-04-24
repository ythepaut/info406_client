package fr.groupe4.clientprojet.display.dialog.errordialog.view;

import fr.groupe4.clientprojet.display.dialog.errordialog.controller.EventErrorDialog;
import fr.groupe4.clientprojet.display.dialog.errordialog.controller.KeyEventErrorDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;

/**
 * Dialog d'erreur
 */
public class ErrorDialog extends DrawDialog {
    /**
     * Message affiché
     */
    @NotNull
    private final String message;

    /**
     * Couleur du texte
     */
    @NotNull
    private final Color color;

    /**
     * Event de fenêtre
     */
    @NotNull
    private final EventErrorDialog eventErrorDialog;

    /**
     * Event de touches
     */
    @NotNull
    private final KeyEventErrorDialog keyEventErrorDialog;

    /**
     * Constructeur custom
     *
     * @param message Message
     * @param title Titre
     * @param color Couleur
     */
    public ErrorDialog(@NotNull String message, @NotNull String title, @NotNull Color color) {
        this.message = message;
        setTitle(title);
        this.color = color;

        setModal(true);
        setSize(510, 80);
        setResizable(false);
        eventErrorDialog = new EventErrorDialog(this);
        addWindowListener(eventErrorDialog);
        keyEventErrorDialog = new KeyEventErrorDialog(this);
        addKeyListener(keyEventErrorDialog);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        drawContent();

        setVisible(true);
    }

    /**
     * Constructeur basique
     *
     * @param message Message
     */
    public ErrorDialog(String message) {
        this(message, "ERREUR", Theme.POLICE_ERROR.getColor(Parameters.getThemeName()));
    }

    /**
     * Affichage
     */
    @Override
    protected void drawContent() {
        setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        setLayout(new BorderLayout());


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        JLabel errorLabel = new JLabel(message);
        errorLabel.setForeground(color);
        errorLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        panel.add(errorLabel, c);

        JButton closeButton = new JButton("Fermer");
        closeButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        closeButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        closeButton.addActionListener(eventErrorDialog);
        closeButton.addKeyListener(keyEventErrorDialog);
        c.gridy++;
        panel.add(closeButton, c);

        add(panel);
    }
}
