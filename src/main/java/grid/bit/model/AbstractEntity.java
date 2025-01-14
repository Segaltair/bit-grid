package grid.bit.model;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author <a href="mailto:dm.artyom@gmail.com">Artyom Dmitriev</a>
 */
@MappedSuperclass
public class AbstractEntity<ID extends Serializable> implements Persistable<ID> {
    private ID id;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractEntity)) {
            return false;
        }
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}