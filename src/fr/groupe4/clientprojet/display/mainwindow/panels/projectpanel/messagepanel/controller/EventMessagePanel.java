package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.messagepanel.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.messagepanel.enums.MessageButton;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.messagepanel.view.MessagePanel;
import fr.groupe4.clientprojet.model.message.enums.MessageResource;

import java.awt.event.*;

/**
 * Le listener de la messagerie
 */
public class EventMessagePanel implements ActionListener {
    /**
     * Le MessagePanel qui appelle les events
     */
    private final MessagePanel source;
    /**
     * Le destinataire des messages
     */
    private final MessageResource dest;

    /**
     * Le constructeur
     *
     * @param source : Le MessagePanel
     */
    public EventMessagePanel(MessagePanel source, MessageResource dest) {
        this.source = source;
        this.dest = dest;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (MessageButton.getEnum(e.getActionCommand())) {
            case SEND:
                sendMessage();
                break;

            case REFRESH:
                source.refresh();
                break;

            default:
        }
    }

    /**
     * Envoie un message
     */
    private void sendMessage() {
        String message = source.getMessage();
        System.out.print(message + " -> ");
        while (!message.isEmpty() && message.charAt(0) == ' ') {
            message = message.substring(1);
        }
        if (!message.isEmpty()) {
            message = "<html>" + message + "</html>";

            StringBuilder res = new StringBuilder();
            for (int i = 0; i < message.length(); i++) {
                if (message.charAt(i) == '\n') {
                    res.append("<br/>");
                } else {
                    res.append(message.charAt(i));
                }
            }

            System.out.println(res);

            Communication.builder().
                    sendMessage(res.toString(), dest, source.getIdProject()).
                    sleepUntilFinished().
                    startNow().
                    build();
            source.refresh();
        }
        source.resetMessage();
    }
}
