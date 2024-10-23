package red.tetracube.hubcentral.room;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import red.tetracube.hubcentral.database.entities.HubEntity;
import red.tetracube.hubcentral.database.entities.RoomEntity;
import red.tetracube.hubcentral.database.repositories.HubRepository;
import red.tetracube.hubcentral.database.repositories.RoomRepository;
import red.tetracube.hubcentral.domain.Result;
import red.tetracube.hubcentral.exceptions.HubCentralException;
import red.tetracube.hubcentral.room.payloads.RoomPayload;

import java.util.UUID;

@ApplicationScoped
public class RoomServices {

    @Inject
    RoomRepository roomRepository;

    @Inject
    HubRepository hubRepository;

    @Transactional
    public Result<RoomPayload.RoomResponse> createRoom(String hubSlug, RoomPayload.CreateRequest request) {
        HubEntity hub;
        try {
            hub = hubRepository.getHubBySlug(hubSlug)
                    .orElseThrow(() -> new HubCentralException.EntityNotFoundException("Hub not found " + hubSlug));
        } catch (HubCentralException.EntityNotFoundException e) {
            return Result.failed(e);
        }

        var room = new RoomEntity();
        room.setId(UUID.randomUUID());
        room.setSlug(request.name().trim().replaceAll(" ", "_").toLowerCase());
        room.setName(request.name().trim());
        room.setHub(hub);
        try {
            roomRepository.save(room);
        } catch (Exception ignored) {
            return Result.failed(
                    new HubCentralException.EntityExistsException("The room already exists" + request.name())
            );
        }
        return Result.success(
                new RoomPayload.RoomResponse(
                        room.getSlug(),
                        room.getName()
                )
        );
    }

    @Transactional
    public Result<RoomPayload.GetRoomsResponse> getRooms(String hubSlug) {
        var rooms = roomRepository.getAllByHub(hubSlug).stream()
                .map(room -> new RoomPayload.RoomResponse(room.getSlug(), room.getName()))
                .toList();

        return Result.success(new RoomPayload.GetRoomsResponse(rooms));
    }

}
