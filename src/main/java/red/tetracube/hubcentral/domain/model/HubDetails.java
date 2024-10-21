package red.tetracube.hubcentral.domain.model;

import java.util.List;

public class HubDetails extends HubBase {

    private final List<Room> rooms;

    public HubDetails(String slug, String name, List<Room> rooms) {
        super(slug, name);
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

}
