package fr.groupe4.clientprojet.display.view.messagepanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.view.RoundButton;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.display.view.messagepanel.controller.EventMessagePanel;
import fr.groupe4.clientprojet.model.message.Message;
import fr.groupe4.clientprojet.model.message.MessageList;
import fr.groupe4.clientprojet.utils.Location;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * Panel de messagerie
 */
public class MessagePanel extends DrawPanel {
    /**
     * La liste des messages
     */
    private MessageList messageList;
    /**
     * L'entrée texte d'envoie de message
     */
    private JTextField messageField;

    /**
     * Le constructeur
     *
     * @param messageList : L'instance de communication qui contient la liste de messages
     */
    public MessagePanel(Communication messageList) {
        this.messageList = (MessageList) messageList.getResult();

        drawContent();
    }

    /**
     * Dessine le contenu
     */
    @Override
    protected void drawContent() {
        setLayout(new BorderLayout());

        // Panel du bas
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        messageField = new JTextField();
        bottomPanel.add(messageField, BorderLayout.CENTER);
        RoundButton sentButton = new RoundButton(new File(Location.getPath() + "/data/img/sent.png"));
        sentButton.addActionListener(new EventMessagePanel(this));
        bottomPanel.add(sentButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Liste des messages
        JPanel messagePanel = new JPanel();
        if (!messageList.isEmpty()) {
            messagePanel.setLayout(new GridLayout(messageList.size(), 1));
            for (Message message: messageList) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(new JLabel(message.getSrc().getFirstname() + " " + message.getSrc().getLastname()), BorderLayout.WEST);
                panel.add(new JLabel(message.getContent()), BorderLayout.CENTER);
                messagePanel.add(panel);
            }
        } else {
            messagePanel.setLayout(new GridBagLayout());
            messagePanel.add(new JLabel("<html><h2><strong>Aucun message !</strong></h2></html>"));
        }
        add(messagePanel, BorderLayout.CENTER);
    }

    /**
     * Renvoie le message qui doit être envoyé
     *
     * @return : String
     */
    public String getMessage() {
        return messageField.getText();
    }

    /**
     * Vide le textField du message
     */
    public void resetMessage() {
        messageField.setText("");
    }
}
