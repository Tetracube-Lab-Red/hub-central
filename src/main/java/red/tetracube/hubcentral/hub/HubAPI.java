package red.tetracube.hubcentral.hub;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import red.tetracube.hubcentral.hub.payloads.CreateHubRequestPayload;
import red.tetracube.hubcentral.hub.payloads.HubPayload;
import red.tetracube.hubcentral.domain.exceptions.HubCentralException;

@Path("/")
public class HubAPI {

    @Inject
    HubServices hubServices;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public HubPayload createHub(@Valid @RequestBody CreateHubRequestPayload request) {
        var result = hubServices.create(request);
        if (result.isSuccess()) {
            return result.getContent();
        }
        var exception = result.getException();
        if (exception instanceof HubCentralException.EntityExistsException) {
            throw new ClientErrorException("Hub already configured", Response.Status.CONFLICT);
        } else {
            throw new InternalServerErrorException(exception);
        }
    }

}
