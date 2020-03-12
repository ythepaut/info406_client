package fr.groupe4.clientprojet.display.view.messagepanel.controller;

import fr.groupe4.clientprojet.display.view.messagepanel.view.MessagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Le listener de la messagerie
 */
public class EventMessagePanel implements ActionListener {
    /**
     * Le MessagePanel qui appelle les events
     */
    private MessagePanel source;

    /**
     * Le constructeur
     *
     * @param source : Le MessagePanel
     */
    public EventMessagePanel(MessagePanel source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = source.getMessage();
        while (!message.isEmpty() && message.charAt(0) == ' ') {
            message = message.substring(1);
        }
        if (!message.isEmpty()) {
            // TODO : envoyer le message
        }
        source.resetMessage();
    }
}
