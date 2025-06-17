package finalmission.domain.restaurant.detail;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    private String address;

    public Address(final String address) {
        this.address = address;
    }

    protected Address() {
    }

    public String getValue() {
        return address;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Address address1)) {
            return false;
        }

        return Objects.equals(address, address1.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }
}
