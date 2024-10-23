package red.tetracube.hubcentral.room.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RoomPayload {

    public record CreateRequest(
            @Pattern(regexp = "^[ \\w]+$") @Size(min = 5, max = 25) @NotNull @NotEmpty @JsonProperty("name") String name
    ) {

    }

    public record RoomResponse(
            String slug,
            String name
    ) {
    }

    public record GetRoomsResponse(
           List<RoomResponse> rooms
    ) {
    }

}
