package red.tetracube.hubcentral.room;

import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import red.tetracube.hubcentral.domain.exceptions.HubCentralException;
import red.tetracube.hubcentral.room.payloads.CreateRoomRequestPayload;
import red.tetracube.hubcentral.room.payloads.RoomPayload;
import red.tetracube.hubcentral.room.payloads.RoomsListPayload;

import java.util.UUID;

@RequestScoped
@Authenticated
@Path("/rooms")
public class RoomAPI {

    @Inject
    JsonWebToken jwt;

    @Inject
    RoomServices roomServices;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public RoomPayload createRoom(@Valid @RequestBody CreateRoomRequestPayload request) {
        var result = roomServices.createRoom(getHubId(), request);
        if (result.isSuccess()) {
            return result.getContent();
        }
        var exception = result.getException();
        if (exception instanceof HubCentralException.EntityNotFoundException) {
            throw new NotFoundException(exception.getMessage());
        } else if (exception instanceof HubCentralException.EntityExistsException) {
            throw new ClientErrorException(exception.getMessage(), Response.Status.CONFLICT);
        }  else {
            throw new InternalServerErrorException(exception);
        }
    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public RoomsListPayload getRooms() {
        var result = roomServices.getRooms(getHubId());
        if (result.isSuccess()) {
            return result.getContent();
        }
        throw new InternalServerErrorException(result.getException());
    }

    private UUID getHubId() {
        return UUID.fromString(jwt.getClaim("hub_id"));
    }

}
