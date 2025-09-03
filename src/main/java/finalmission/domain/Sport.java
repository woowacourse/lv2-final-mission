package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "sport")
@Entity
public class Sport {
    @Column(nullable = false, unique = true)
    String name;
    @Column(nullable = false)
    String description;
    Long reservationLimit = 5L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Sport(final Long id, final String name, final String description, final Long reservationLimit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reservationLimit = reservationLimit;
    }

    protected Sport() {

    }

    public static Sport withoutId(final String name, final String description, final Long reservationLimit) {
        return new Sport(null, name, description, reservationLimit);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getReservationLimit() {
        return reservationLimit;
    }
}
