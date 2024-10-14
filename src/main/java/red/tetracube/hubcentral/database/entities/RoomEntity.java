package red.tetracube.hubcentral.database.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class RoomEntity {
    
    @Id
    private UUID id;

    @Column(name = "slug", unique = true, nullable = false)
    private String slug;

    @Column(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "hub_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HubEntity.class)
    private HubEntity hub;

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

}
