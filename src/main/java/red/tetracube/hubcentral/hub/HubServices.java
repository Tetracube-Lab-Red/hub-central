package red.tetracube.hubcentral.hub;

import java.util.UUID;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import red.tetracube.hubcentral.database.entities.HubEntity;
import red.tetracube.hubcentral.domain.exceptions.HubCentralException;
import red.tetracube.hubcentral.domain.Result;
import red.tetracube.hubcentral.hub.payloads.CreateHubRequestPayload;
import red.tetracube.hubcentral.hub.payloads.HubPayload;

@RequestScoped
public class HubServices {

    @Transactional
    public Result<HubPayload> create(CreateHubRequestPayload request) {
        if (HubEntity.count() > 0) {
            return Result.failed(
                    new HubCentralException.EntityExistsException("There is another hub configured")
            );
        }

        var hub = new HubEntity();
        hub.id = UUID.randomUUID();
        hub.name = request.name().trim();
        hub.accessCode = BcryptUtil.bcryptHash(request.password());
        hub.persist();
        return Result.success(
                new HubPayload(
                        hub.name
                )
        );
    }

}
