package red.tetracube.hubcentral.database.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hubs")
public class HubEntity extends PanacheEntityBase {

    @Id
    public UUID id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "access_code", nullable = false)
    public String accessCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hub", orphanRemoval = true, targetEntity = RoomEntity.class)
    public List<RoomEntity> rooms = new ArrayList<>();

}
