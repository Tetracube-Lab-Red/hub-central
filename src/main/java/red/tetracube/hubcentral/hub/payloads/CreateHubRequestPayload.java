package red.tetracube.hubcentral.hub.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateHubRequestPayload(
        @Pattern(regexp = "^[ \\w]+$")
        @Size(min = 5, max = 25)
        @NotNull
        @NotEmpty
        @JsonProperty("name")
        String name,

        @NotNull
        @NotEmpty
        @JsonProperty("password")
        String password
) {
}
