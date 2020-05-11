package fr.groupe4.clientprojet.display.dialog.connectiondialog.view;

import fr.groupe4.clientprojet.display.dialog.connectiondialog.controller.EventConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.controller.KeyEventConnectionDialog;
import fr.groupe4.clientprojet.display.dialog.connectiondialog.enums.ConnectionChoice;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import org.jetbrains.annotations.NotNull;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

/**
 * Le dialog de connexion
 */
public class ConnectionDialog extends DrawDialog {
    /**
     * Le champ texte pour l'identifiant
     */
    private JTextField usernameTextField;

    /**
     * Le champ texte pour le mot de passe
     */
    private JPasswordField passwordTextField;

    /**
     * Le listener du dialog
     */
    private final EventConnectionDialog eventConnectionDialog;

    /**
     * Le constructeur
     */
    public ConnectionDialog() {
        super(null);
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

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        passwordPanel.setBorder(new EmptyBorder(10, 20, 90, 20));

        JLabel usernameLabel = new JLabel("Username :");
        usernameLabel.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        usernamePanel.add(usernameLabel, BorderLayout.CENTER);

        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        passwordPanel.add(passwordLabel, BorderLayout.CENTER);

        usernameTextField = new JTextField(100);
        usernameTextField.setBorder(null);
        usernameTextField.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        usernameTextField.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        usernameTextField.addKeyListener(keyEventConnectionDialog);

        passwordTextField = new JPasswordField(100);
        passwordTextField.setBorder(null);
        passwordTextField.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        passwordTextField.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        passwordTextField.addKeyListener(keyEventConnectionDialog);

        usernamePanel.add(usernameTextField, BorderLayout.SOUTH);
        fieldPanel.add(usernamePanel);

        passwordPanel.add(passwordTextField, BorderLayout.SOUTH);
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
     * @return Identifiant
     */
    @NotNull
    public String getUsername() {
        return usernameTextField.getText();
    }

    /**
     * Renvoie le mot de passe
     *
     * @return Mot de passe
     */
    @NotNull
    public String getPassword() {
        StringBuilder res = new StringBuilder();
        char[] cs = passwordTextField.getPassword();
        for (char c : cs) {
            res.append(c);
        }
        return res.toString();
    }

    /**
     * Vide le champs mot de passe
     */
    public void resetPassword() {
        passwordTextField.setText("");
    }
}
