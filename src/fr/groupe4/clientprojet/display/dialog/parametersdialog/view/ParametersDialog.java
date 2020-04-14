package fr.groupe4.clientprojet.display.dialog.parametersdialog.view;

import fr.groupe4.clientprojet.display.dialog.parametersdialog.controller.EventParametersDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.parameters.themes.ThemeName;

import javax.swing.*;
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
     * Tous les champs paramètres
     */
    private JTextField serverUrlField;
    private JComboBox themeChoice;

    /**
     * Le constructeur
     */
    public ParametersDialog() {
        setTitle("Paramètres");
        setSize(1000, 600);
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
        slide.addSlide(generalPanel(), "Général");
        slide.addSlide(appearancePanel(), "Apparence");
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

    private JPanel generalPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        JPanel urlPanel = new JPanel(new GridLayout(1, 2));
        urlPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
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

    private JPanel appearancePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        JPanel themePanel = new JPanel(new GridLayout(1, 2));
        themePanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        JLabel label = new JLabel("Thème :");
        label.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        themePanel.add(label);
        themeChoice = new JComboBox();
        themeChoice.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        themeChoice.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        themeChoice.addItem(ThemeName.CLAIR);
        themeChoice.addItem(ThemeName.SOMBRE);
        themeChoice.setSelectedItem(Parameters.getThemeName());
        themePanel.add(themeChoice);
        panel.add(themePanel, c);

        return panel;
    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.
    // Getters des valeurs des champs de paramètres

    public String getServerUrl() {
        return serverUrlField.getText();
    }

    public ThemeName getTheme() {
        return (ThemeName) themeChoice.getSelectedItem();
    }
}
