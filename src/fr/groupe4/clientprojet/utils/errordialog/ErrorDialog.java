package fr.groupe4.clientprojet.utils.errordialog;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends JDialog {
    private String message;
    private EventErrorDialog eventErrorDialog;

    public ErrorDialog(JDialog owner, String message) {
        super(owner);
        this.message = message;
        eventErrorDialog = new EventErrorDialog(this);
        setSize(350, 80);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(eventErrorDialog);
        setTitle("ERREUR");

        drawContent();

        setVisible(true);
    }

    private void drawContent() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        JLabel errorLabel = new JLabel(message);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        add(errorLabel, c);
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(eventErrorDialog);
        c.gridy = 1;
        add(closeButton, c);
    }
}
