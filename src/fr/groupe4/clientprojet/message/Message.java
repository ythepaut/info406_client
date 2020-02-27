package fr.groupe4.clientprojet.message;

import fr.groupe4.clientprojet.message.enums.MessageOrigin;

import java.util.Date;

public class Message {
    private long id;

    private long date;

    private long idSrc;

    private long idDst;

    private MessageOrigin dst;

    private String content;

    public Message(long id, long date, long idSrc, long idDst, String dst, String content) {
        this.id = id;
        this.date = date;
        this.idSrc = idSrc;
        this.idDst = idDst;
        this.dst = MessageOrigin.fromString(dst);
        this.content = content;
    }

    public String toString() {
        return new Date(date*1000).toString();
    }
}
