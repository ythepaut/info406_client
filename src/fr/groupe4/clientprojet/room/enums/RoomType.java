package fr.groupe4.clientprojet.room.enums;

import fr.groupe4.clientprojet.communication.enums.CommunicationPropertyName;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

public enum RoomType {
    MEETING_ROOM("MEETING_ROOM"),
    CONFERENCE_ROOM("CONFERENCE_ROOM"),
    DESK("DESK"),
    OTHER("OTHER"),
    ROOM("ROOM");

    private String type;

    RoomType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toUpperCase();
    }

    @NotNull
    public static RoomType fromString(String name) throws IllegalArgumentException {
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
