package fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.controller.EventUserPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.enums.UserChoice;
import fr.groupe4.clientprojet.display.view.RoundButton;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.resource.human.User;
import fr.groupe4.clientprojet.utils.Location;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;

/**
 * Le panel pour l'utilisateur
 */
public class UserPanel extends DrawPanel {
    /**
     * Le champs de modification du mot de passe
     */
    private JPasswordField passwordField;
    /**
     * Le champs de mofication du mail
     */
    private JTextField mailField;
    /**
     * Le listener du panel
     */
    private EventUserPanel eventUserPanel;
    /**
     * L'utilisateur
     */
    private User user;

    /**
     * Le constructeur
     */
    public UserPanel() {
        setLayout(new GridLayout(2, 1));
        eventUserPanel = new EventUserPanel(this);
        user = (User) Communication.builder().getUserInfos().startNow().sleepUntilFinished().build().getResult();

        drawContent();
    }

    /**
     * Dessine le contenu
     */
    @Override
    protected void drawContent() {
        setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        // Partie supérieure
        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        topPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        JPanel descripPanel = new JPanel(new GridLayout(1, 2));
        descripPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        descripPanel.add(new JLabel(new ImageIcon(Location.getImgDataPath() + "/user.png")));
        JPanel namePanel = new JPanel(new GridLayout(2, 1));
        namePanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        JLabel label = new JLabel("<html><h1 style='font-size:2em;'>" +
                user.getFirstname() + "<br/>" +
                user.getLastname() + "</h1></html>");
        label.setForeground(Theme.POLICE_ACCENT.getColor(Parameters.getThemeName()));
        namePanel.add(label);
        label = new JLabel("<html><p style='text-align:justify;'><em>" +
                user.getDescription() + "</em></p></html>");
        label.setForeground(Theme.POLICE_ACCENT.getColor(Parameters.getThemeName()));
        namePanel.add(label);
        descripPanel.add(namePanel);
        descripPanel.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, Theme.BORDER.getColor(Parameters.getThemeName())),
                new EmptyBorder(0, 0, 0, 20)));
        topPanel.add(descripPanel);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        GridBagConstraints c = new GridBagConstraints();
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        label = new JLabel("Mot de passe :");
        label.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        passwordPanel.add(label, BorderLayout.NORTH);
        passwordField = new JPasswordField();
        passwordField.setBorder(null);
        passwordField.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        passwordField.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        JButton passwordButton = new JButton("Modifier");
        passwordButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        passwordButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        passwordButton.setActionCommand(UserChoice.PASSWORD.getName());
        passwordButton.addActionListener(eventUserPanel);
        passwordPanel.add(passwordButton, BorderLayout.SOUTH);
        c.gridx = 0;
        c.gridy = 0;
        rightPanel.add(passwordPanel, c);

        JPanel mailPanel = new JPanel(new BorderLayout());
        mailPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        label = new JLabel("Adressse mail :");
        label.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        mailPanel.add(label, BorderLayout.NORTH);
        mailField = new JTextField(user.getEmail());
        mailField.setBorder(null);
        mailField.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        mailField.setBackground(Theme.FOND_FIELD.getColor(Parameters.getThemeName()));
        mailPanel.add(mailField, BorderLayout.CENTER);
        JButton mailButton = new JButton("Modifier");
        mailButton.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
        mailButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        mailButton.setActionCommand(UserChoice.MAIL.getName());
        mailButton.addActionListener(eventUserPanel);
        mailPanel.add(mailButton, BorderLayout.SOUTH);
        c.gridy++;
        rightPanel.add(mailPanel, c);

        JPanel settingsPanel = new JPanel(new GridLayout(1, 1));
        settingsPanel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        settingsPanel.setBorder(new EmptyBorder(0, 50, 0, 0));
        RoundButton settingsButton = new RoundButton(new File(Location.getImgDataPath() + "/settings.png"));
        settingsButton.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
        settingsButton.setActionCommand(UserChoice.SETTINGS.getName());
        settingsButton.addActionListener(eventUserPanel);
        settingsPanel.add(settingsButton);

        c.gridy = 0;
        c.gridx = 1;
        c.gridheight = 2;
        rightPanel.add(settingsPanel, c);
        topPanel.add(rightPanel);

        add(topPanel);

        // Partie inférieure (fil d'actualité)
        /*
        final int nbNews = 10;
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(50, 100, 50, 10));
        bottomPanel.add(new JLabel("Fil d'actualité :"), BorderLayout.NORTH);
        JPanel newsPanel = new JPanel(new GridLayout(nbNews, 1));
        for (int i = 0; i < nbNews; i++) {
            newsPanel.add(new JLabel("TODAY : " + i));
        }
        bottomPanel.add(newsPanel, BorderLayout.CENTER);
        add(bottomPanel);*/
    }

    /**
     * Renvoie le mot de passe dans le passwordField
     *
     * @return : le mot de passe
     */
    public String getPassword() {
        StringBuilder res = new StringBuilder();
        char[] cs = passwordField.getPassword();
        for (char c : cs) {
            res.append(c);
        }
        return res.toString();
    }

    /**
     * Récupère l'adresse mail dans le mailField
     *
     * @return : l'adresse mail
     */
    public String getEmail() {
        return mailField.getText();
    }
}
