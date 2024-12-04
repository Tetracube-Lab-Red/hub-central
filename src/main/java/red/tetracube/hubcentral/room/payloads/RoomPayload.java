package red.tetracube.hubcentral.room.payloads;

import java.util.UUID;

public record RoomPayload(
        UUID id,
        String name
) {

}
