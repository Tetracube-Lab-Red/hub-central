package red.tetracube.hubcentral.database.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import red.tetracube.hubcentral.database.entities.RoomEntity;

@ApplicationScoped
public class RoomRepository {

    @Inject
    EntityManager entityManager;

    public RoomEntity save(RoomEntity room) {
        var savedEntity = entityManager.merge(room);
        entityManager.flush();
        return savedEntity;
    }

}
