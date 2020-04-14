package fr.groupe4.clientprojet.display.dialog.connectiondialog.view;

import fr.groupe4.clientprojet.display.dialog.connectiondialog.controller.EventConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.controller.KeyEventConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.enums.ConnectionChoice;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Le dialog de connexion
 */
public class ConnectionDialog extends DrawDialog {
    /**
     * Le champ texte pour l'identifiant
     */
    private JTextField username;
    /**
     * Le champ texte pour le mot de passe
     */
    private JPasswordField password;
    /**
     * Le listener du dialog
     */
    private EventConnectionDialog eventConnectionDialog;

    /**
     * Le constructeur
     */
    public ConnectionDialog() {
        setTitle("Connexion");
        setModal(true);
        setSize(250, 350);
        setResizable(false);
        eventConnectionDialog = new EventConnectionDialog(this);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(eventConnectionDialog);

        drawContent();

        setVisible(true);
    }

    /**
     * Dessine le contenu du dialog
     */
    @Override
    protected void drawContent() {
        setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        setLayout(new BorderLayout());
        KeyEventConnectionDialog keyEventConnectionDialog = new KeyEventConnectionDialog(this);

        JPanel fieldPanel = new JPanel(new GridLayout(2, 1));
        fieldPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        usernamePanel.setBorder(new EmptyBorder(80, 20, 20, 20));
        JLabel label = new JLabel("Username :");
        label.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        usernamePanel.add(label, BorderLayout.CENTER);
        username = new JTextField(100);
        username.setBorder(null);
        username.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        username.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        username.addKeyListener(keyEventConnectionDialog);
        usernamePanel.add(username, BorderLayout.SOUTH);
        fieldPanel.add(usernamePanel);
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        passwordPanel.setBorder(new EmptyBorder(10, 20, 90, 20));
        label = new JLabel("Password :");
        label.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        passwordPanel.add(label, BorderLayout.CENTER);
        password = new JPasswordField(100);
        password.setBorder(null);
        password.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        password.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        password.addKeyListener(keyEventConnectionDialog);
        passwordPanel.add(password, BorderLayout.SOUTH);
        fieldPanel.add(passwordPanel);
        add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        JButton okButton = new JButton("OK");
        okButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        okButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        okButton.setActionCommand(ConnectionChoice.OK.getName());
        okButton.addActionListener(eventConnectionDialog);
        buttonPanel.add(okButton);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        cancelButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        cancelButton.setActionCommand(ConnectionChoice.CANCEL.getName());
        cancelButton.addActionListener(eventConnectionDialog);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Renvoie l'identifiant
     *
     * @return : l'identifiant
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * Renvoie le mot de passe
     *
     * @return : le mot de passe
     */
    public String getPassword() {
        StringBuilder res = new StringBuilder();
        char[] cs = password.getPassword();
        for (char c : cs) {
            res.append(c);
        }
        return res.toString();
    }

    /**
     * Vide le champs mot de passe
     */
    public void resetPassword() {
        password.setText("");
    }
}
