package red.tetracube.hubcentral.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HubData(
        @JsonProperty("slug")
        String slug,

        @JsonProperty("name")
        String name,

        @JsonProperty("token")
        String token
) {
}
