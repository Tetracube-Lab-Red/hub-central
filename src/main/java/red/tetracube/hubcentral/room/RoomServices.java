package red.tetracube.hubcentral.room;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import red.tetracube.hubcentral.database.entities.HubEntity;
import red.tetracube.hubcentral.database.entities.RoomEntity;
import red.tetracube.hubcentral.domain.Result;
import red.tetracube.hubcentral.domain.exceptions.HubCentralException;
import red.tetracube.hubcentral.room.payloads.CreateRoomRequestPayload;
import red.tetracube.hubcentral.room.payloads.RoomPayload;
import red.tetracube.hubcentral.room.payloads.RoomsListPayload;

import java.util.UUID;

@ApplicationScoped
public class RoomServices {

    @Transactional
    public Result<RoomPayload> createRoom(UUID hubId, CreateRoomRequestPayload request) {
        HubEntity hub;
        try {
            hub = HubEntity.<HubEntity>findByIdOptional(hubId)
                    .orElseThrow(() -> new HubCentralException.EntityNotFoundException("Hub not found " + hubId));
        } catch (HubCentralException.EntityNotFoundException e) {
            return Result.failed(e);
        }

        var room = new RoomEntity();
        room.id = UUID.randomUUID();
        room.name = request.name().trim();
        room.hub = hub;
        try {
            room.persist();
        } catch (Exception ignored) {
            return Result.failed(
                    new HubCentralException.EntityExistsException("The room already exists" + request.name())
            );
        }
        return Result.success(
                new RoomPayload(
                        room.id,
                        room.name
                )
        );
    }

    @Transactional
    public Result<RoomsListPayload> getRooms(UUID hubId) {
        var rooms = RoomEntity.<RoomEntity>find("hub.id", hubId)
                .stream()
                .map(room -> new RoomPayload(room.id, room.name))
                .toList();

        return Result.success(new RoomsListPayload(rooms));
    }

}
