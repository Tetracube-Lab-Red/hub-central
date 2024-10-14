package red.tetracube.hubcentral.api;

import io.quarkus.security.UnauthorizedException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import red.tetracube.hubcentral.api.payloads.HubPayload;
import red.tetracube.hubcentral.exceptions.HubCentralException;
import red.tetracube.hubcentral.services.HubServices;

import java.util.Collections;

@Path("/")
public class HubAPI {

    @Inject
    JsonWebToken jwt;

    @Inject
    HubServices hubServices;

    private final static Logger LOG = LoggerFactory.getLogger(HubAPI.class);

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public HubPayload.Reply createHub(@Valid @RequestBody HubPayload.CreateRequest request) {
        var result = hubServices.create(request.name(), request.password());
        if (result.isSuccess()) {
            var hubEntity = result.getContent();
            return new HubPayload.Reply(
                    hubEntity.getSlug(),
                    hubEntity.getName(),
                    Collections.emptyList()
            );
        }
        var exception = result.getException();
        if (exception instanceof HubCentralException.EntityExistsException) {
            throw new ClientErrorException("Hub already configured", Response.Status.CONFLICT);
        } else {
            throw new InternalServerErrorException(exception);
        }
    }

    @Authenticated
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public HubPayload.Reply getInfo() {
        var slug = (String) jwt.claim("hub_slug").orElseThrow(() -> new UnauthorizedException("User has not a valid token"));
        var result = hubServices.getHubBySlug(slug);
        if (result.isSuccess()) {
            return result.getContent();
        }
        var exception = result.getException();
        if (exception instanceof HubCentralException.RepositoryException) {
            throw new InternalServerErrorException("Internal hub error");
        } else if (exception instanceof HubCentralException.EntityNotFoundException) {
            throw new UnauthorizedException();
        } else {
            throw new InternalServerErrorException(exception);
        }
    }
}
