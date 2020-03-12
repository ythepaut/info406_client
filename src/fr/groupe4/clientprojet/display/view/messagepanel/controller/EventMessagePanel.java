package fr.groupe4.clientprojet.display.view.messagepanel.controller;

import fr.groupe4.clientprojet.display.view.messagepanel.view.MessagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventMessagePanel implements ActionListener {
    private MessagePanel source;

    public EventMessagePanel(MessagePanel source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!source.getMessage().isEmpty()) {
            // TODO : envoyer le message
        }
    }
}
