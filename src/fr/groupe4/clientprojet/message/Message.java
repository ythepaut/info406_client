package fr.groupe4.clientprojet.message;

import fr.groupe4.clientprojet.message.enums.MessageResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Message {
    /**
     * Id du message
     */
    private long id;

    /**
     * Date d'envoi
     */
    @NotNull
    private LocalDateTime date;

    /**
     * Id de l'émetteur
     */
    @NotNull
    private HumanResource src;

    /**
     * Id du destinataire
     */
    private long idDst;

    /**
     * Type de ressource de la destination
     */
    @NotNull
    private MessageResource dst;

    /**
     * Contenu du message
     */
    @NotNull
    private String content;

    public Message(@NotNull HumanResource src, long id, long date, long idDst, @NotNull String dst, @NotNull String content) {
        this.id = id;
        this.date = Instant.ofEpochMilli(date*1000).atZone(ZoneId.systemDefault()).toLocalDateTime();;
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

    @NotNull
    @Override
    public String toString() {
        return src.getFirstname() + " " + src.getLastname() + ", " + getDate() + " : " + content;
    }
}
