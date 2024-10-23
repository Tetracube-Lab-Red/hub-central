package red.tetracube.hubcentral.database.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import red.tetracube.hubcentral.database.entities.RoomEntity;

import java.util.List;

@ApplicationScoped
public class RoomRepository {

    @Inject
    EntityManager entityManager;

    public List<RoomEntity> getAllByHub(String hubSlug) throws EntityExistsException {
        var query = """
                from RoomEntity room
                left join fetch room.hub h
                where h.slug = :hubSlug
                """;
        return entityManager.createQuery(query, RoomEntity.class)
                .setParameter("hubSlug", hubSlug)
                .getResultList();
    }

    public void save(RoomEntity room) throws EntityExistsException {
        entityManager.persist(room);
    }

}
