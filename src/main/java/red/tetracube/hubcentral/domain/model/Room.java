package red.tetracube.hubcentral.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {

    @JsonProperty()
    private final String slug;

    @JsonProperty()
    private final String name;

    public Room(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

}
