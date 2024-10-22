package red.tetracube.hubcentral.auth.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LoginPayload {

    public record Request(
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

    public record Response(
            @JsonProperty("slug")
            String slug,

            @JsonProperty("name")
            String name,

            @JsonProperty("token")
            String token
    ) {
    }

}
