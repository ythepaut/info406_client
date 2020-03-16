package fr.groupe4.clientprojet.display.view.messagepanel.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.view.messagepanel.enums.MessageButton;
import fr.groupe4.clientprojet.display.view.messagepanel.view.MessagePanel;
import fr.groupe4.clientprojet.model.message.enums.MessageResource;

import java.awt.event.*;

/**
 * Le listener de la messagerie
 */
public class EventMessagePanel extends KeyAdapter implements ActionListener, MouseWheelListener {
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

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMessage();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        source.setDebutListe(source.getDebutListe() + e.getWheelRotation());
        source.redraw();
    }

    /**
     * Envoie un message
     */
    private void sendMessage() {
        String message = source.getMessage();
        while (!message.isEmpty() && message.charAt(0) == ' ') {
            message = message.substring(1);
        }
        if (!message.isEmpty()) {
            Communication.builder().sendMessage(message, MessageResource.MESSAGE_RESOURCE_PROJECT, source.getIdProject()).sleepUntilFinished().startNow().build();
            source.refresh();
        }
        source.resetMessage();
    }
}
