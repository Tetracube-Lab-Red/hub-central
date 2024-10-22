package red.tetracube.hubcentral.room;

import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import red.tetracube.hubcentral.exceptions.HubCentralException;
import red.tetracube.hubcentral.room.payloads.RoomPayload;

@RequestScoped
@Authenticated
@Path("/rooms")
public class RoomAPI {

    @Inject
    JsonWebToken jwt;

    @Inject
    @Claim(value = "hub_slug")
    String hubSlug;

    @Inject
    RoomServices roomServices;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public RoomPayload.CreateResponse createRoom(@Valid @RequestBody RoomPayload.CreateRequest request) {
        var result = roomServices.createRoom(hubSlug, request);
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

}
