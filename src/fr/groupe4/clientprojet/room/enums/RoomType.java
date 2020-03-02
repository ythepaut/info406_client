package fr.groupe4.clientprojet.room.enums;

import fr.groupe4.clientprojet.communication.enums.CommunicationPropertyName;

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

    public static RoomType fromString(String name) {
        RoomType[] vars = RoomType.values();

        RoomType result = null;

        for (RoomType var : vars) {
            if (var.type.equalsIgnoreCase(name)) {
                result = var;
            }
        }

        return result;
    }
}
