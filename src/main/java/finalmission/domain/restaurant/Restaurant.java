package finalmission.domain.restaurant;

import finalmission.domain.restaurant.detail.Address;
import finalmission.domain.restaurant.detail.Description;
import finalmission.domain.restaurant.detail.Name;
import finalmission.domain.restaurant.detail.RestaurantDetail;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private RestaurantDetail detail;

    protected Restaurant() {
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return detail.getName();
    }

    public Address getAddress() {
        return detail.getAddress();
    }

    public Description getDescription() {
        return detail.getDescription();
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Restaurant that)) {
            return false;
        }

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
