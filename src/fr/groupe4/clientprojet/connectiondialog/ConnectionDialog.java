package fr.groupe4.clientprojet.connectiondialog;

import fr.groupe4.clientprojet.connectiondialog.enums.ConnectionChoice;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConnectionDialog extends JDialog {
    private EventConnectionDialog eventConnectionDialog;

    public ConnectionDialog(JFrame owner) {
        super(owner, true);
        setUndecorated(true);

        drawContent();

        setVisible(true);
    }

    private void drawContent() {
        eventConnectionDialog = new EventConnectionDialog(this);

        setSize(300, 400);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        setContentPane(new JPanel());

        BorderLayout mainLayout = new BorderLayout();

        JPanel formPanel = new JPanel(new GridLayout(2, 1));

        JPanel idPanel = new JPanel(new GridLayout(2, 1));
        idPanel.setBorder(new EmptyBorder(60, 50, 60, 50));
        JLabel textLabel = new JLabel("Votre identifiant :");
        idPanel.add(textLabel);
        JTextField textField = new JTextField();
        textField.setBorder(new EmptyBorder(10, 10, 10, 10));
        idPanel.add(textField);
        formPanel.add(idPanel);
        eventConnectionDialog.setIdentifiant(textField);

        JPanel passwordPanel = new JPanel(new GridLayout(2, 1));
        passwordPanel.setBorder(new EmptyBorder(0, 50, 120, 50));
        JLabel passwordLabel = new JLabel("Votre mot de passe :");
        passwordPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(new EmptyBorder(10, 10, 10, 10));
        passwordPanel.add(passwordField);
        formPanel.add(passwordPanel);
        eventConnectionDialog.setPassword(passwordField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBorder(new EmptyBorder(0, 20, 5, 20));
        JButton okButton = new JButton("OK");
        okButton.setActionCommand(ConnectionChoice.OK.getName());
        okButton.addActionListener(eventConnectionDialog);
        buttonPanel.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(ConnectionChoice.CANCEL.getName());
        cancelButton.addActionListener(eventConnectionDialog);
        buttonPanel.add(cancelButton);

        getContentPane().setLayout(mainLayout);
        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);



    }

}
