package red.tetracube.hubcentral.auth;

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
import red.tetracube.hubcentral.auth.payloads.LoginRequestPayload;
import red.tetracube.hubcentral.auth.payloads.LoginResponsePayload;
import red.tetracube.hubcentral.domain.exceptions.HubCentralException;

@Path("/auth")
public class HubAuthAPI {

    @Inject
    HubAuthServices hubAuthServices;

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public LoginResponsePayload login(@Valid @RequestBody LoginRequestPayload loginRequest) {
        var result = hubAuthServices.generateTokenForHub(loginRequest);
        if (result.isSuccess()) {
            return result.getContent();
        }
        var exception = result.getException();
        switch (exception) {
            case HubCentralException.EntityNotFoundException ignored ->
                    throw new UnauthorizedException();
            case HubCentralException.UnauthorizedException ignored ->
                    throw new UnauthorizedException();
            case null, default ->
                    throw new InternalServerErrorException(exception);
        }
    }

}
