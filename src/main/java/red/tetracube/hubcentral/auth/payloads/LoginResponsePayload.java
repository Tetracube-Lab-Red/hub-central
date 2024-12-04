package red.tetracube.hubcentral.auth.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record LoginResponsePayload(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        String name,

        @JsonProperty("token")
        String token
) {
}
