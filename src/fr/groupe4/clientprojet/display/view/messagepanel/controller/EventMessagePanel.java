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
    private MessageResource dest;

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

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMessage();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        source.setDebutListe(source.getDebutListe() + e.getWheelRotation());
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
            Communication.builder().
                    sendMessage(message, dest, source.getIdProject()).
                    sleepUntilFinished().
                    startNow().
                    build();
            source.refresh();
        }
        source.setDebutListeMax();
        source.resetMessage();
    }
}
