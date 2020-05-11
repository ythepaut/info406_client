package fr.groupe4.clientprojet.display.dialog.parametersdialog.view;

import fr.groupe4.clientprojet.display.dialog.parametersdialog.controller.EventParametersDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.display.view.slide.SlideItem;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.parameters.themes.ThemeName;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

/**
 * Le dialog des paramètres
 */
public class ParametersDialog extends DrawDialog {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    /**
     * Le listener du dialog
     */
    private final EventParametersDialog eventParametersDialog;

    /**
     * Tous les champs paramètres
     */
    private JTextField serverUrlField;

    /**
     * Choix des thèmes
     */
    private JComboBox<ThemeName>themeChoice;

    /**
     * Le constructeur
     */
    public ParametersDialog() {
        setTitle("Paramètres");
        setSize(WIDTH, HEIGHT);
        setModal(true);
        eventParametersDialog = new EventParametersDialog(this);
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
        setBackground(Theme.FOND.getColor(Parameters.getThemeName()));

        Slide slide = new Slide();
        slide.addSlide(new SlideItem("Général", generalPanel()));
        slide.addSlide(new SlideItem("Apparence", appearancePanel()));
        add(slide, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        JButton okButton = new JButton("OK");
        okButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        okButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        okButton.setActionCommand(EventParametersDialog.OK);
        okButton.addActionListener(eventParametersDialog);
        bottomPanel.add(okButton);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        cancelButton.setActionCommand(EventParametersDialog.CANCEL);
        cancelButton.addActionListener(eventParametersDialog);
        bottomPanel.add(cancelButton);
        JButton applyButton = new JButton("Appliquer");
        applyButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        applyButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        applyButton.setActionCommand(EventParametersDialog.APPLY);
        applyButton.addActionListener(eventParametersDialog);
        bottomPanel.add(applyButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Fonction initialisant les variables
     *
     * @param panel_1 Panel 1
     * @param panel_2 Panel 2
     * @param c Contrainte
     */
    private static void initPanel(JPanel panel_1, JPanel panel_2, GridBagConstraints c) {
        panel_1.setLayout(new GridBagLayout());
        panel_1.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));

        c.gridx = 0;
        c.gridy = 0;

        panel_2.setLayout(new GridLayout(1, 2));
        panel_2.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
    }

    /**
     * Construit le panel général
     *
     * @return Panel général
     */
    private JPanel generalPanel() {
        JPanel panel = new JPanel();
        JPanel urlPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();

        initPanel(panel, urlPanel, c);

        JLabel label = new JLabel("Url serveur API : ");
        label.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        urlPanel.add(label);
        serverUrlField = new JTextField(Parameters.getServerUrl());
        serverUrlField.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        serverUrlField.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        serverUrlField.setBorder(null);
        urlPanel.add(serverUrlField);
        panel.add(urlPanel, c);

        return panel;
    }

    /**
     * Construit le panel d'apparence
     *
     * @return Panel
     */
    private JPanel appearancePanel() {
        JPanel panel = new JPanel();
        JPanel themePanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();

        initPanel(panel, themePanel, c) ;

        JLabel label = new JLabel("Thème :");
        label.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        themePanel.add(label);
        themeChoice = new JComboBox<>();
        themeChoice.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        themeChoice.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        themeChoice.addItem(ThemeName.CLAIR);
        themeChoice.addItem(ThemeName.SOMBRE);
        themeChoice.setSelectedItem(Parameters.getThemeName());
        themePanel.add(themeChoice);
        panel.add(themePanel, c);

        return panel;
    }

    // Getters des valeurs des champs de paramètres

    /**
     * Récupère l'URL
     *
     * @return URL
     */
    @NotNull
    public String getServerUrl() {
        return serverUrlField.getText();
    }

    /**
     * Récupère le thème
     *
     * @return Thème
     */
    @NotNull
    public ThemeName getTheme() {
        ThemeName theme = (ThemeName) themeChoice.getSelectedItem();

        if (theme == null) {
            Logger.error("Thème nul");
            throw new IllegalStateException("Thème nul");
        }

        return theme;
    }
}
