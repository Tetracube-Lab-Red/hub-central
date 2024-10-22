package red.tetracube.hubcentral.room;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import red.tetracube.hubcentral.database.entities.HubEntity;
import red.tetracube.hubcentral.database.entities.RoomEntity;
import red.tetracube.hubcentral.database.repositories.HubRepository;
import red.tetracube.hubcentral.database.repositories.RoomRepository;
import red.tetracube.hubcentral.domain.Result;
import red.tetracube.hubcentral.exceptions.HubCentralException;
import red.tetracube.hubcentral.room.payloads.RoomPayload;

@ApplicationScoped
public class RoomServices {

    @Inject
    RoomRepository roomRepository;

    @Inject
    HubRepository hubRepository;

    public Result<RoomPayload.CreateResponse> createRoom(String hubSlug, RoomPayload.CreateRequest request) {
        HubEntity hub = null;
        try {
            hub = hubRepository.getHubBySlug(hubSlug)
                    .orElseThrow(() -> new HubCentralException.EntityNotFoundException("Hub not found " + hubSlug));
        } catch (HubCentralException.EntityNotFoundException e) {
            return Result.failed(e);
        }
        var room = new RoomEntity();
        room.setSlug(request.name().trim().replaceAll(" ", "_").toLowerCase());
        room.setName(request.name().trim());
        room.setHub(hub);
        var createdEntity = roomRepository.save(room);
        return Result.success(
                new RoomPayload.CreateResponse(
                        createdEntity.getSlug(),
                        createdEntity.getName()
                )
        );
    }

}
