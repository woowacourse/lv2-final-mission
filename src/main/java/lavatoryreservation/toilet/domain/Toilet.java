package lavatoryreservation.toilet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lavatoryreservation.lavatory.domain.Lavatory;

@Entity
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    private Lavatory lavatory;
    private boolean isBidet;

    public Toilet(Long id, String description, boolean isBidet, Lavatory lavatory) {
        this.id = id;
        this.description = description;
        this.isBidet = isBidet;
        this.lavatory = lavatory;
    }

    protected Toilet() {

    }

    public String getDescription() {
        return description;
    }

    public Lavatory getLavatory() {
        return lavatory;
    }

    public Long getId() {
        return id;
    }
}
