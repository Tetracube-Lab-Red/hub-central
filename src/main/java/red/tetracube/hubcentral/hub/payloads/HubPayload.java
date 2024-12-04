package red.tetracube.hubcentral.hub.payloads;

import java.util.UUID;

public record HubPayload(
        UUID slug,
        String name
) {

}
