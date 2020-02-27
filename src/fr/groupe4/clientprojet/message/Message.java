package fr.groupe4.clientprojet.message;

import fr.groupe4.clientprojet.message.enums.MessageResource;
import fr.groupe4.clientprojet.resource.human.HumanResource;

import java.util.Date;

public class Message {
    /**
     * Id du message
     */
    private long id;

    /**
     * Date d'envoi
     */
    private long date;

    /**
     * Id de l'émetteur
     */
    private HumanResource src;

    /**
     * Id du destinataire
     */
    private long idDst;

    /**
     * Type de ressource de la destination
     */
    private MessageResource dst;

    /**
     * Contenu du message
     */
    private String content;

    public Message(HumanResource src, long id, long date, long idDst, String dst, String content) {
        this.id = id;
        this.date = date;
        this.src = src;
        this.idDst = idDst;
        this.dst = MessageResource.fromString(dst);
        this.content = content;
    }

    public Date getDate() {
        return new Date(date*1000);
    }

    public String toString() {
        return src.getFirstname() + " " + src.getLastname() + ", " + getDate() + " : " + content;
    }
}
