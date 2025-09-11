package finalmission.toilet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    public Toilet(Long id, String position) {
        this.id = id;
        this.position = position;
    }

    public Toilet(String position) {
        this.position = position;
    }

    protected Toilet() {
    }

    public Long getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Toilet toilet)) {
            return false;
        }
        return Objects.equals(id, toilet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
