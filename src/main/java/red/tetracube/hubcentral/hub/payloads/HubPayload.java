package red.tetracube.hubcentral.hub.payloads;

import java.util.UUID;

public record HubPayload(
        UUID id,
        String name
) {

}
