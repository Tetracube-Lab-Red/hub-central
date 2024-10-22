package red.tetracube.hubcentral.database.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import red.tetracube.hubcentral.database.entities.RoomEntity;

@ApplicationScoped
public class RoomRepository {

    @Inject
    EntityManager entityManager;

    public void save(RoomEntity room) throws EntityExistsException {
        entityManager.persist(room);
    }

}
