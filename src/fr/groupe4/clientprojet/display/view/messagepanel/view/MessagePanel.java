package fr.groupe4.clientprojet.display.view.messagepanel.view;

import fr.groupe4.clientprojet.communication.CommunicationBuilder;
import fr.groupe4.clientprojet.display.view.RoundButton;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.display.view.messagepanel.controller.EventMessagePanel;
import fr.groupe4.clientprojet.display.view.messagepanel.enums.MessageButton;
import fr.groupe4.clientprojet.model.message.Message;
import fr.groupe4.clientprojet.model.message.MessageList;
import fr.groupe4.clientprojet.model.resource.human.User;
import fr.groupe4.clientprojet.utils.Location;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
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
     * L'id du projet
     */
    private long idProject;
    /**
     * Le listener du panel
     */
    private EventMessagePanel eventMessagePanel;
    /**
     * L'instance de CommunicationBuidler pour récuperer la liste des messages
     */
    private CommunicationBuilder cBuilder;

    /**
     * Le constructeur
     *
     * @param cBuilder : L'instance de communicationBuilder pour récuperer la liste des messages
     */
    public MessagePanel(CommunicationBuilder cBuilder) {
        this.cBuilder = cBuilder;
        refresh();
        eventMessagePanel = new EventMessagePanel(this);

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
        messageField.addKeyListener(eventMessagePanel);
        bottomPanel.add(messageField, BorderLayout.CENTER);
        RoundButton sentButton = new RoundButton(new File(Location.getImgDataPath() + "/sent.png"));
        sentButton.setActionCommand(MessageButton.SEND.toString());
        sentButton.addActionListener(eventMessagePanel);
        bottomPanel.add(sentButton, BorderLayout.EAST);
        RoundButton refreshButton = new RoundButton(new File(Location.getImgDataPath() + "/refresh.png"));
        refreshButton.setActionCommand(MessageButton.REFRESH.toString());
        refreshButton.addActionListener(eventMessagePanel);
        bottomPanel.add(refreshButton, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Liste des messages
        JPanel messagePanel = new JPanel();
        if (messageList != null && !messageList.isEmpty()) {
            messagePanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = c.gridy = 0;
            c.insets = new Insets(10, 0, 10, 0);

            for (Message message: messageList) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(new JLabel(message.getContent()), BorderLayout.CENTER);

                JLabel sender = new JLabel(message.getSrc().getFirstname() + " " + message.getSrc().getLastname());
                sender.setBorder(new EmptyBorder(0, 20, 0, 20));
                if (message.getSrc().getResourceId() == User.getUser().getResourceId()) {
                    panel.add(sender, BorderLayout.EAST);
                    panel.setBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK));
                    c.anchor = GridBagConstraints.LINE_END;
                } else {
                    panel.add(sender, BorderLayout.WEST);
                    panel.setBorder(new MatteBorder(0, 2, 0, 0, Color.BLACK));
                    c.anchor = GridBagConstraints.LINE_START;
                }

                messagePanel.add(panel, c);
                c.gridy++;
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

    /**
     * Modifie l'id du projet
     *
     * @param idProject : l'id du projet
     */
    public void setIdProject(long idProject) {
        this.idProject = idProject;
    }

    /**
     * Renvoie l'id du projet
     *
     * @return : l'id du projet
     */
    public long getIdProject() {
        return idProject;
    }

    /**
     * Rafraichi la liste des messages
     * Redessine le panel si liste différente
     */
    public void refresh() {
        System.out.println(cBuilder.build().getMessage());
        MessageList temp = (MessageList) cBuilder.startNow().sleepUntilFinished().build().getResult();
        if (temp != null && !temp.equals(messageList)) {
            messageList = temp;
            redraw();
        } else {
            messageList = temp;
        }
    }
}
