package fr.groupe4.clientprojet.display.dialog.errordialog.view;

import fr.groupe4.clientprojet.display.dialog.errordialog.controller.EventErrorDialog;
import fr.groupe4.clientprojet.display.dialog.errordialog.controller.KeyEventErrorDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends DrawDialog {
    private String message;
    private Color color;

    private EventErrorDialog eventErrorDialog;
    private KeyEventErrorDialog keyEventErrorDialog;


    public ErrorDialog(String message, String title, Color color) {
        this.message = message;
        setTitle(title);
        this.color = color;

        setModal(true);
        setSize(510, 80);
        setResizable(false);
        eventErrorDialog = new EventErrorDialog(this);
        addWindowListener(eventErrorDialog);
        keyEventErrorDialog = new KeyEventErrorDialog(this);
        addKeyListener(keyEventErrorDialog);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        drawContent();

        setVisible(true);
    }

    public ErrorDialog(String message) {
        this(message, "ERREUR", Color.RED);
    }

    @Override
    protected void drawContent() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        JLabel errorLabel = new JLabel(message);
        errorLabel.setForeground(color);
        errorLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        add(errorLabel, c);

        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(eventErrorDialog);
        closeButton.addKeyListener(keyEventErrorDialog);
        c.gridy++;
        add(closeButton, c);
    }
}
