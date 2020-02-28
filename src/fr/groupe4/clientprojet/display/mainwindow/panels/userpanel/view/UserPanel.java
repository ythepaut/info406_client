package fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.controller.EventUserPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.enums.UserChoice;
import fr.groupe4.clientprojet.resource.human.User;
import fr.groupe4.clientprojet.utils.Location;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Le panel pour l'utilisateur
 */
public class UserPanel extends JPanel {
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
    private void drawContent() {
        // Partie supérieure
        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        JPanel descripPanel = new JPanel(new GridLayout(1, 2));
        descripPanel.add(new JLabel(new ImageIcon(Location.getPath() + "/data/img/user.png")));
        JPanel namePanel = new JPanel(new GridLayout(2, 1));
        namePanel.add(new JLabel("<html><h1 style='font-size:2em;'>" + user.getFirstname() + "<br/>" + user.getLastname() + "</h1></html>"));
        namePanel.add(new JLabel("<html><p style='text-align:justify;'><em>" + user.getDescription() + "</em></p></html>"));
        descripPanel.add(namePanel);
        descripPanel.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK), new EmptyBorder(0, 0, 0, 20)));
        topPanel.add(descripPanel);


        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(new JLabel("Mot de passe :"), BorderLayout.NORTH);
        passwordField = new JPasswordField();
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        JButton passwordButton = new JButton("Modifier");
        passwordButton.setActionCommand(UserChoice.PASSWORD.getName());
        passwordButton.addActionListener(eventUserPanel);
        passwordPanel.add(passwordButton, BorderLayout.SOUTH);
        c.gridx = c.gridy = 0;
        rightPanel.add(passwordPanel, c);

        JPanel mailPanel = new JPanel(new BorderLayout());
        mailPanel.add(new JLabel("Adressse mail :"), BorderLayout.NORTH);
        mailField = new JTextField(user.getEmail());
        mailPanel.add(mailField, BorderLayout.CENTER);
        JButton mailButton = new JButton("Modifier");
        mailButton.setActionCommand(UserChoice.MAIL.getName());
        mailButton.addActionListener(eventUserPanel);
        mailPanel.add(mailButton, BorderLayout.SOUTH);
        c.gridy = 1;
        rightPanel.add(mailPanel, c);
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

    /**
     * Redessine le panel
     */
    public void redraw() {
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }
}
