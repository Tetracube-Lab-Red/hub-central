package red.tetracube.hubcentral.hub;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import red.tetracube.hubcentral.database.entities.HubEntity;
import red.tetracube.hubcentral.database.repositories.HubRepository;
import red.tetracube.hubcentral.exceptions.HubCentralException;
import red.tetracube.hubcentral.domain.Result;
import red.tetracube.hubcentral.hub.payloads.HubPayload;

@RequestScoped
public class HubServices {

    @Inject
    HubRepository hubRepository;

    private final static Logger LOG = LoggerFactory.getLogger(HubServices.class);

    @Transactional
    public Result<HubPayload.CreateResponse> create(String name, String password) {
        if (hubRepository.hubExists()) {
            return Result.failed(
                    new HubCentralException.EntityExistsException("There is another hub configured")
            );
        }

        var hub = new HubEntity();
        hub.setId(UUID.randomUUID());
        hub.setName(name.trim());
        hub.setSlug(name.trim().replaceAll(" ", "_").toLowerCase());
        hub.setAccessCode(BcryptUtil.bcryptHash(password));
        hubRepository.save(hub);
        return Result.success(
                new HubPayload.CreateResponse(
                        hub.getSlug(),
                        hub.getName()
                )
        );
    }

}
