package fr.groupe4.clientprojet.connectiondialog;

import fr.groupe4.clientprojet.connectiondialog.enums.ConnectionChoice;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConnectionDialog extends JDialog {
    private JTextField username;
    private JPasswordField password;
    private EventConnectionDialog eventConnectionDialog;

    public ConnectionDialog(JFrame owner) {
        super(owner, "Connexion", true);
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

    private void drawContent() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel fieldPanel = new JPanel(new GridLayout(2, 1));
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBorder(new EmptyBorder(90, 20, 20, 20));
        usernamePanel.add(new JLabel("Username :"), BorderLayout.CENTER);
        username = new JTextField(100);
        usernamePanel.add(username, BorderLayout.SOUTH);
        fieldPanel.add(usernamePanel);
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBorder(new EmptyBorder(10, 20, 100, 20));
        passwordPanel.add(new JLabel("Password :"), BorderLayout.CENTER);
        password = new JPasswordField(100);
        passwordPanel.add(password, BorderLayout.SOUTH);
        fieldPanel.add(passwordPanel);
        add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        JButton okButton = new JButton("OK");
        okButton.setActionCommand(ConnectionChoice.OK.getName());
        okButton.addActionListener(eventConnectionDialog);
        buttonPanel.add(okButton, c);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setActionCommand(ConnectionChoice.CANCEL.getName());
        cancelButton.addActionListener(eventConnectionDialog);
        c.gridx = 1;
        buttonPanel.add(cancelButton, c);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        StringBuilder res = new StringBuilder();
        char[] cs = password.getPassword();
        for (char c : cs) {
            res.append(c);
        }
        return res.toString();
    }
}
