package red.tetracube.hubcentral.api.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class HubPayload {

    public record CreateRequest(
            @Pattern(regexp = "^[ \\w]+$") @Size(min = 5, max = 25) @NotNull @NotEmpty @JsonProperty("name") String name,
            @NotNull @NotEmpty @JsonProperty("password") String password
    ) {

    }

    public record Reply(
            String slug,
            String name,
            List<RoomPayload> rooms
    ) {

    }

    public record RoomPayload(
            String slug,
            String name
    ) {

    }

}
