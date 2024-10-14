package red.tetracube.hubcentral.database.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hubs")
public class HubEntity {

    @Id
    private UUID id;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "access_code", nullable = false)
    private String accessCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hub", orphanRemoval = true, targetEntity = RoomEntity.class)
    private List<RoomEntity> rooms = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
