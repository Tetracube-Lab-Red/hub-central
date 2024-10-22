package red.tetracube.hubcentral.room.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RoomPayload {

    public record CreateRequest(
            @Pattern(regexp = "^[ \\w]+$") @Size(min = 5, max = 25) @NotNull @NotEmpty @JsonProperty("name") String name
    ) {

    }

    public record CreateResponse(
            String slug,
            String name
    ) {
    }

}
