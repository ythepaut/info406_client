package fr.groupe4.clientprojet.model.room.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

/**
 * Type de salle
 */
public enum RoomType {
    MEETING_ROOM("MEETING_ROOM"),
    CONFERENCE_ROOM("CONFERENCE_ROOM"),
    DESK("DESK"),
    OTHER("OTHER"),
    ROOM("ROOM");

    /**
     * Type de salle
     */
    @NotNull
    private final String type;

    /**
     * Constructeur
     *
     * @param type Type
     */
    RoomType(@NotNull String type) {
        this.type = type;
    }

    /**
     * Vers String
     *
     * @return String
     */
    @NotNull
    @Override
    public String toString() {
        return type;
    }

    /**
     * String vers enum
     *
     * @param name Nom
     * @return Enum
     * @throws IllegalArgumentException Si l'enum n'est pas trouvée
     */
    @NotNull
    public static RoomType fromString(@NotNull String name) throws IllegalArgumentException {
        RoomType[] vars = RoomType.values();

        RoomType result = null;

        for (RoomType var : vars) {
            if (var.type.equalsIgnoreCase(name)) {
                result = var;
            }
        }

        if (result == null) {
            String errorMsg = "Pas d'enum provenant de la chaine '" + name + "'";
            Logger.error(errorMsg, LoggerOption.LOG_FILE_ONLY);
            throw new IllegalArgumentException(errorMsg);
        }

        return result;
    }
}
