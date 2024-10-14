package red.tetracube.hubcentral.database.repositories;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import red.tetracube.hubcentral.database.entities.HubEntity;

@ApplicationScoped
public class HubRepository {

    @Inject
    EntityManager em;

    @Transactional
    public Optional<HubEntity> getHubByName(String name) {
        var query = """
                from HubEntity h
                where h.name = :name
                """;
        try {
            var hub = em.createQuery(query, HubEntity.class)
                    .setParameter("name", name)
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.ofNullable(hub);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<HubEntity> getHubBySlug(String slug) {
        try {
            var hub = em.createQuery("""
                                    from HubEntity h
                                    where h.slug = :slug
                                    """,
                            HubEntity.class)
                    .setParameter("slug", slug)
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.ofNullable(hub);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Boolean hubExists() {
        return !em.createQuery(
                        "from HubEntity h",
                        HubEntity.class
                )
                .getResultList()
                .isEmpty();
    }

    public HubEntity save(HubEntity hub) {
        var savedEntity = em.merge(hub);
        em.flush();
        return savedEntity;
    }

}
