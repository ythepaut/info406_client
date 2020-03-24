package fr.groupe4.clientprojet.display.view.messagepanel.view;

import fr.groupe4.clientprojet.communication.CommunicationBuilder;
import fr.groupe4.clientprojet.display.view.RoundButton;
import fr.groupe4.clientprojet.display.view.ScrollBarUI;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.display.view.messagepanel.controller.EventMessagePanel;
import fr.groupe4.clientprojet.display.view.messagepanel.enums.MessageButton;
import fr.groupe4.clientprojet.model.message.Message;
import fr.groupe4.clientprojet.model.message.MessageList;
import fr.groupe4.clientprojet.model.message.enums.MessageResource;
import fr.groupe4.clientprojet.model.resource.human.User;
import fr.groupe4.clientprojet.utils.Location;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

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
     * Le début de la liste des messages
     */
    private int debutListe = -1;
    /**
     * Le nombre de messages pouvant être affiché en même temps
     */
    private static final int nbMessageMax = 11;

    /**
     * Le constructeur
     *
     * @param cBuilder : L'instance de communicationBuilder pour récuperer la liste des messages
     */
    public MessagePanel(CommunicationBuilder cBuilder) {
        this.cBuilder = cBuilder;
        refresh();
        eventMessagePanel = new EventMessagePanel(this, MessageResource.MESSAGE_RESOURCE_PROJECT);
        addMouseWheelListener(eventMessagePanel);

        drawContent();
    }

    /**
     * Dessine le contenu
     */
    @Override
    protected void drawContent() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(Color.WHITE);

        // Panel du bas
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        messageField = new JTextField(120);
        messageField.grabFocus();
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
        drawMessageList();
    }

    private void drawMessageList() {
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(Color.WHITE);
        if (messageList != null && !messageList.isEmpty()) {
            messagePanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(5, 0, 5, 0);
            c.fill = GridBagConstraints.HORIZONTAL;

            JScrollPane scrollPane = new JScrollPane(messagePanel);
            scrollPane.setBackground(Color.WHITE);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
            scrollPane.getHorizontalScrollBar().setUI(new ScrollBarUI());

            for (Message message: messageList) {
                JPanel panel = new JPanel(new BorderLayout());

                JLabel content = new JLabel(message.getContent());
                panel.add(content, BorderLayout.CENTER);

                JPanel infoPanel = new JPanel(new GridLayout(2, 1));
                if (message.getDate().isAfter(LocalDateTime.now().minusDays(1))) { //Si le message est d'aujourd'hui
                    infoPanel.add(new JLabel(message.getDate().getHour() + ":" + message.getDate().getMinute()));
                } else {
                    infoPanel.add(new JLabel(message.getDate().
                            format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))));
                }
                infoPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 0),
                        new CompoundBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK),
                                new EmptyBorder(0, 0, 0, 5))));

                assert User.getUser() != null;
                infoPanel.add(new JLabel(User.getUser().isSender(message) ?
                        "Moi" :
                        message.getSrc().getFirstname() + " " + message.getSrc().getLastname()));
                panel.add(infoPanel, BorderLayout.WEST);
                content.setBorder(new EmptyBorder(0, 10, 0, 10));
                c.anchor = GridBagConstraints.WEST;

                Color fond = User.getUser().isSender(message) ?
                        new Color(200, 200, 200) :
                        new Color(240, 240, 240);
                panel.setBackground(fond);
                infoPanel.setBackground(fond);

                messagePanel.add(panel, c);
                c.gridy++;
            }
            add(scrollPane, BorderLayout.CENTER);
        } else {
            messagePanel.setLayout(new GridBagLayout());
            messagePanel.add(new JLabel("<html><div style=\"text-align:center;\">" +
                    "<h2><strong>Aucun message</strong></h2>" +
                    "<p>Envoyez en un premier !</p></div></html>"));
            add(messagePanel, BorderLayout.CENTER);
        }

        /*messagePanel.setBackground(Color.WHITE);
        if (messageList != null && !messageList.isEmpty()) {
            messagePanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = c.gridy = 0;
            c.insets = new Insets(5, 0, 5, 0);
            c.fill = GridBagConstraints.HORIZONTAL;

            int i = 0;
            for (Message message: messageList) {
                if (i >= debutListe && i < debutListe + nbMessageMax) {
                    JPanel panel = new JPanel(new BorderLayout());

                    JLabel content = new JLabel(message.getContent());
                    panel.add(content, BorderLayout.CENTER);

                    JPanel infoPanel = new JPanel(new GridLayout(2, 1));
                    if (message.getDate().isAfter(LocalDateTime.now().minusDays(1))) { //Si le message est d'aujourd'hui
                        infoPanel.add(new JLabel(message.getDate().getHour() + ":" + message.getDate().getMinute()));
                    } else {
                        infoPanel.add(new JLabel(message.getDate().
                                format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))));
                    }
                    infoPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 0),
                            new CompoundBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK),
                                    new EmptyBorder(0, 0, 0, 5))));

                    infoPanel.add(new JLabel(User.getUser().isSender(message) ?
                            "Moi" :
                            message.getSrc().getFirstname() + " " + message.getSrc().getLastname()));
                    panel.add(infoPanel, BorderLayout.WEST);
                    content.setBorder(new EmptyBorder(0, 10, 0, 10));
                    c.anchor = GridBagConstraints.WEST;

                    Color fond = User.getUser().isSender(message) ?
                            new Color(200, 200, 200) :
                            new Color(240, 240, 240);
                    panel.setBackground(fond);
                    infoPanel.setBackground(fond);

                    messagePanel.add(panel, c);
                    c.gridy++;
                }
                i++;
            }
        } else {
            messagePanel.setLayout(new GridBagLayout());
            messagePanel.add(new JLabel("<html><div style=\"text-align:center;\">" +
                    "<h2><strong>Aucun message</strong></h2>" +
                    "<p>Envoyez en un premier !</p></div></html>"));
        }
        add(messagePanel, BorderLayout.CENTER);*/
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
     * Modifie le début de la liste
     *
     * @param debutListe : Le début de la liste
     */
    public void setDebutListe(int debutListe) {
        this.debutListe = Math.max(0, Math.min(debutListe, messageList.size() - nbMessageMax));
        redraw();
    }

    public void setDebutListeMax() {
        setDebutListe(messageList.size());
    }

    /**
     * Renvoie le début de la liste
     *
     * @return int
     */
    public int getDebutListe() {
        return debutListe;
    }

    /**
     * Rafraichi la liste des messages
     * Redessine le panel si liste différente
     */
    public void refresh() {
        messageList = (MessageList) cBuilder.startNow().sleepUntilFinished().build().getResult();
        if (debutListe == -1 && messageList != null) {
            setDebutListeMax();
        }
        redraw();
    }
}
