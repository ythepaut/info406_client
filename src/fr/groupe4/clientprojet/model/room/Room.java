package fr.groupe4.clientprojet.model.room;

import fr.groupe4.clientprojet.model.room.enums.RoomType;

public class Room {
    private long id;
    private RoomType type;
    private int nbSeats;
    private int nbComputers;

    public Room(long id, String type, long nbSeats, long nbComputers) {
        this(id, RoomType.fromString(type), nbSeats, nbComputers);
    }

    public Room(long id, RoomType type, long nbSeats, long nbComputers) {
        this.id = id;
        this.type = type;
        this.nbSeats = (int) nbSeats;
        this.nbComputers = (int) nbComputers;
    }

    public long getId() {
        return id;
    }
}
