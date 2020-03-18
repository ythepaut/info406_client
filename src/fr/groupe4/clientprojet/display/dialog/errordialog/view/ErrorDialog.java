package fr.groupe4.clientprojet.display.dialog.errordialog.view;

import fr.groupe4.clientprojet.display.dialog.errordialog.controller.EventErrorDialog;
import fr.groupe4.clientprojet.display.dialog.errordialog.controller.KeyEventErrorDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends DrawDialog {
    private String message;
    private EventErrorDialog eventErrorDialog;
    private KeyEventErrorDialog keyEventErrorDialog;

    public ErrorDialog(String message) {
        setModal(true);
        this.message = message;
        eventErrorDialog = new EventErrorDialog(this);
        keyEventErrorDialog = new KeyEventErrorDialog(this);
        setSize(510, 80);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(eventErrorDialog);
        addKeyListener(keyEventErrorDialog);
        setTitle("ERREUR");

        drawContent();

        setVisible(true);
    }

    @Override
    protected void drawContent() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        JLabel errorLabel = new JLabel(message);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        add(errorLabel, c);
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(eventErrorDialog);
        closeButton.addKeyListener(keyEventErrorDialog);
        c.gridy = 1;
        add(closeButton, c);
    }
}
