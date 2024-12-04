package red.tetracube.hubcentral.room.payloads;

import java.util.List;

public record RoomsListPayload(
        List<RoomPayload> rooms
) {
}
