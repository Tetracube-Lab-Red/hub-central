package red.tetracube.hubcentral.database.entities;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class RoomEntity extends PanacheEntityBase {
    
    @Id
    public UUID id;

    @Column(name = "name", unique = true, nullable = false)
    public String name;

    @JoinColumn(name = "hub_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HubEntity.class)
    public HubEntity hub;

}
