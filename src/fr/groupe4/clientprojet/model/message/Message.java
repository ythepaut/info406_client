package fr.groupe4.clientprojet.model.message;

import fr.groupe4.clientprojet.model.message.enums.MessageResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Message
 */
public class Message {
    /**
     * Id du message dans la BDD
     */
    private final long id;

    /**
     * Date d'envoi
     */
    @NotNull
    private final LocalDateTime date;

    /**
     * Id de l'émetteur
     */
    @NotNull
    private final HumanResource src;

    /**
     * Id du destinataire
     */
    private final long idDst;

    /**
     * Type de ressource de la destination
     */
    @NotNull
    private final MessageResource dst;

    /**
     * Contenu du message
     */
    @NotNull
    private final String content;

    public Message(@NotNull HumanResource src, long id, long date, long idDst, @NotNull String dst, @NotNull String content) {
        this.id = id;
        this.date = Instant.ofEpochMilli(date * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.src = src;
        this.idDst = idDst;
        this.dst = MessageResource.fromString(dst);
        this.content = content;
    }

    /**
     * Récupère la date
     *
     * @return Date
     */
    @NotNull
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Vers chaine
     *
     * @return Chaine
     */
    @NotNull
    @Override
    public String toString() {
        return src.getFirstname() + " " + src.getLastname() + ", " + getDate() + " : " + content;
    }

    /**
     * Retourne le contenu du message
     *
     * @return : String
     */
    public String getContent() {
        return content;
    }

    /**
     * Renvoie l'utilisateur source
     *
     * @return
     */
    public HumanResource getSrc() {
        return src;
    }

    /**
     * Renvoie l'utilisateur destinataire
     *
     * @return
     */
    public MessageResource getDst() {
        return dst;
    }
}
