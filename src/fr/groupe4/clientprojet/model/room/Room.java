package fr.groupe4.clientprojet.model.room;

import fr.groupe4.clientprojet.model.room.enums.RoomType;
import org.jetbrains.annotations.NotNull;

/**
 * Salle
 */
public class Room {
    /**
     * Id de la salle
     */
    private final long id;

    /**
     * Type de salle
     */
    @NotNull
    private final RoomType roomType;

    /**
     * Nombre de places de la salle
     */
    private final int nbSeats;

    /**
     * Nombre d'ordinateurs dans la salle
     */
    private final int nbComputers;

    /**
     * Constructeur
     *
     * @param id Id
     * @param roomType Type de salle
     * @param nbSeats Nombre de sièges
     * @param nbComputers Nombre d'ordinateurs
     */
    public Room(long id, @NotNull String roomType, long nbSeats, long nbComputers) {
        this(id, RoomType.fromString(roomType), nbSeats, nbComputers);
    }

    /**
     * Constructeur
     *
     * @param id Id
     * @param roomType Type de salle
     * @param nbSeats Nombre de sièges
     * @param nbComputers Nombre d'ordinateurs
     */
    public Room(long id, @NotNull RoomType roomType, long nbSeats, long nbComputers) {
        this.id = id;
        this.roomType = roomType;
        this.nbSeats = (int) nbSeats;
        this.nbComputers = (int) nbComputers;
    }

    /**
     * Récupère l'id de la salle
     *
     * @return Id
     */
    public long getId() {
        return id;
    }

    /**
     * Récupère le type de salle
     *
     * @return Type de salle
     */
    @NotNull
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Récupère le nombre de sièges dans la salle
     *
     * @return Nombre de sièges
     */
    public int getNbSeats() {
        return nbSeats;
    }

    /**
     * Retourne le nombre d'ordinateurs dans la salle
     *
     * @return Nombre d'ordinateurs
     */
    public int getNbComputers() {
        return nbComputers;
    }
}
