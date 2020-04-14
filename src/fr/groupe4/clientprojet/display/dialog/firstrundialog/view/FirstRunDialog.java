package fr.groupe4.clientprojet.display.dialog.firstrundialog.view;

import fr.groupe4.clientprojet.display.dialog.firstrundialog.controller.EventFirstRunDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FirstRunDialog extends DrawDialog {
    private JTextField urlField;
    private EventFirstRunDialog eventFirstRunDialog;

    public FirstRunDialog() {
        setTitle("Premier lancement");
        setSize(300, 400);
        setModal(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        eventFirstRunDialog = new EventFirstRunDialog(this);
        addWindowListener(eventFirstRunDialog);

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

        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        fieldPanel.setBorder(new EmptyBorder(130, 20, 150, 20));
        JLabel label = new JLabel("Saisissez l'url du serveur :");
        label.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        fieldPanel.add(label);
        urlField = new JTextField(Parameters.getServerUrl());
        urlField.setBorder(null);
        urlField.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        urlField.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        fieldPanel.add(urlField, BorderLayout.SOUTH);

        add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        buttonPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
        JButton okButton = new JButton("Ok");
        okButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        okButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        okButton.setActionCommand(EventFirstRunDialog.OK);
        okButton.addActionListener(eventFirstRunDialog);
        buttonPanel.add(okButton);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        cancelButton.setActionCommand(EventFirstRunDialog.CANCEL);
        cancelButton.addActionListener(eventFirstRunDialog);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getUrl() {
        return urlField.getText();
    }
}
