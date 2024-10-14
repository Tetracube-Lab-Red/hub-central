package red.tetracube.hubcentral.api;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import io.quarkus.security.UnauthorizedException;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import red.tetracube.hubcentral.api.payloads.LoginPayload;
import red.tetracube.hubcentral.exceptions.HubCentralException;
import red.tetracube.hubcentral.services.HubServices;

@Path("/auth")
public class HubAuthAPI {

    @Inject
    HubServices hubServices;

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public LoginPayload.Reply login(@Valid @RequestBody LoginPayload.Request loginRequest) {
        var result = hubServices.generateTokenForHub(loginRequest.name(), loginRequest.accessCode());
        if (result.isSuccess()) {
            var token = result.getContent();
            return new LoginPayload.Reply(token);
        }
        var exception = result.getException();
        switch (exception) {
            case HubCentralException.EntityNotFoundException entityNotFoundException ->
                    throw new UnauthorizedException();
            case HubCentralException.UnauthorizedException unauthorizedException -> throw new UnauthorizedException();
            case null, default -> throw new InternalServerErrorException(exception);
        }
    }

}
