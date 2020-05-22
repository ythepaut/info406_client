package fr.groupe4.clientprojet.display.dialog.errordialog.view;

import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.errordialog.controller.KeyEventErrorDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog d'erreur
 */
public class ErrorDialog extends DrawDialog {
    /**
     * Couleur pour les messages d'erreur ok
     */
    public static final Color COLOR_OK = new Color(0, 127, 0);

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
    private final GenericExitEvent eventErrorDialog;

    /**
     * Event de touches
     */
    @NotNull
    private final KeyEventErrorDialog keyEventErrorDialog;

    /**
     * Constructeur custom
     *
     * @param message Message
     * @param title   Titre
     * @param color   Couleur
     * @param owner   Propriétaire
     */
    public ErrorDialog(@NotNull String message, @NotNull String title, @NotNull Color color, Window owner) {
        super(owner);
        if (color == COLOR_OK) {
            Logger.success("SUCCESS DIALOG:", title, message);
        } else {
            Logger.warning("ERROR DIALOG:", title, message);
        }

        this.message = message;
        setTitle(title);
        this.color = color;

        setModal(true);
        setSize(510, 80);
        setResizable(false);
        eventErrorDialog = new GenericExitEvent(this);
        addWindowListener(eventErrorDialog);
        keyEventErrorDialog = new KeyEventErrorDialog(this);
        addKeyListener(keyEventErrorDialog);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        drawContent();

        setVisible(true);
    }

    /**
     * Constructeur basique
     *
     * @param message Message
     * @param owner Propriétaire
     */
    public ErrorDialog(@NotNull String message, Window owner) {
        this(message, "ERREUR", Theme.POLICE_ERROR.getColor(Parameters.getThemeName()), owner);
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
