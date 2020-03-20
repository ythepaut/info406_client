package fr.groupe4.clientprojet.display.dialog.parametersdialog.view;

import fr.groupe4.clientprojet.display.dialog.parametersdialog.controller.EventParametersDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;
import fr.groupe4.clientprojet.model.parameters.Parameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

        Slide slide = new Slide();
        slide.addSlide(generalPanel(), "Général");
        slide.addSlide(appearancePanel(), "Apparence");
        add(slide, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("OK");
        okButton.setActionCommand(EventParametersDialog.OK);
        okButton.addActionListener(eventParametersDialog);
        bottomPanel.add(okButton);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setActionCommand(EventParametersDialog.CANCEL);
        cancelButton.addActionListener(eventParametersDialog);
        bottomPanel.add(cancelButton);
        JButton applyButton = new JButton("Appliquer");
        applyButton.setActionCommand(EventParametersDialog.APPLY);
        applyButton.addActionListener(eventParametersDialog);
        bottomPanel.add(applyButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel generalPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;

        JPanel urlPanel = new JPanel(new GridLayout(1, 2));
        urlPanel.add(new JLabel("Url serveur API : "));
        serverUrlField = new JTextField(Parameters.getServerUrl());
        urlPanel.add(serverUrlField);
        panel.add(urlPanel);

        return panel;
    }

    private JPanel appearancePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Apparence"));
        return panel;
    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.
    // Getters des valeurs des champs de paramètres

    public String getServerUrl() {
        return serverUrlField.getText();
    }
}
