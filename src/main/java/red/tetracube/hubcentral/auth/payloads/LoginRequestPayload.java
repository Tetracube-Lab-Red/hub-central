package red.tetracube.hubcentral.auth.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginRequestPayload(
        @NotNull
        @NotEmpty
        @JsonProperty("name")
        String name,

        @NotNull
        @NotEmpty
        @JsonProperty("accessCode")
        String accessCode
) {

}
