package grid.bit;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Persistable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.commons.util.CollectionUtils.getOnlyElement;

@Transactional
@Rollback
@SpringBootTest
@SqlConfig(encoding = "UTF-8")
public class AbstractIntegrationTest {
    @PersistenceContext
    private EntityManager em;

    protected final <T extends Persistable<?>, S> T findByName(Class<T> tClass, S name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(tClass);
        Root<T> root = cq.from(tClass);
        CriteriaQuery<T> all = cq.select(root).where(cb.equal(root.get("name"), name));
        List<T> list = em.createQuery(all).setMaxResults(1).getResultList();
        return list.isEmpty() ? null : getOnlyElement(list);
    }

    protected final <ID, T extends Persistable<ID>> T find(Class<T> tClass, ID pk) {
        return em.find(tClass, pk);
    }

    protected final <T extends Persistable<?>> T findOnly(Class<T> tClass) {
        List<T> list = findAll(tClass);
        assertThat(list).hasSize(1);
        return list.get(0);
    }

    protected final <T> List<T> findAll(Class<T> tClass) {
        TypedQuery<T> allQuery = makeAllQuery(tClass, null);
        return allQuery.getResultList();
    }

    protected final <T extends Persistable<?>> T findFirst(Class<T> tClass) {
        TypedQuery<T> allQuery = makeAllQuery(tClass, true);
        List<T> list = allQuery.setMaxResults(1).getResultList();
        return list.isEmpty() ? null : getOnlyElement(list);
    }

    protected final <T extends Persistable<?>> T findLast(Class<T> tClass) {
        List<T> list = findLast(tClass, 1);
        return list.isEmpty() ? null : getOnlyElement(list);
    }

    protected final <T extends Persistable<?>> List<T> findLast(Class<T> tClass, int count) {
        TypedQuery<T> allQuery = makeAllQuery(tClass, false);
        return allQuery.setMaxResults(count).getResultList();
    }

    private <T> TypedQuery<T> makeAllQuery(Class<T> tClass, Boolean asc) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(tClass);
        Root<T> root = cq.from(tClass);
        CriteriaQuery<T> all = cq.select(root);
        if (asc != null) {
            all = all.orderBy(asc ? cb.asc(root.get("id")) : cb.desc(root.get("id")));
        }
        return em.createQuery(all);
    }

    protected final <T extends Persistable<?>> long count(Class<T> tClass) {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(tClass)));
        return em.createQuery(cq).getSingleResult();
    }

    protected final long count(String dbEntityName) {
        return (long) em.createNativeQuery("SELECT COUNT(1) FROM " + dbEntityName).getSingleResult();
    }

    protected void flush() {
        em.flush();
    }

    protected void flushAndClear() {
        em.flush();
        em.clear();
    }
}
