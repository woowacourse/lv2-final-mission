package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    protected Toilet() {
    }

    public Toilet(Long id, String position) {
        this.id = id;
        this.position = position;
    }

    public Toilet(String position) {
        this(null, position);
    }

    public Long getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }
}
