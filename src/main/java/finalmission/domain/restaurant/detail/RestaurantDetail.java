package finalmission.domain.restaurant.detail;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.Objects;

@Embeddable
public class RestaurantDetail {

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    @Embedded
    private Description description;

    protected RestaurantDetail() {
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Description getDescription() {
        return description;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final RestaurantDetail that)) {
            return false;
        }

        return Objects.equals(getName(), that.getName()) && Objects.equals(getAddress(),
                that.getAddress()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getName());
        result = 31 * result + Objects.hashCode(getAddress());
        result = 31 * result + Objects.hashCode(getDescription());
        return result;
    }
}
