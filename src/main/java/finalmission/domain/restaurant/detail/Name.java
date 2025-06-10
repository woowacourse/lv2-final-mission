package finalmission.domain.restaurant.detail;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    private String name;

    public Name(final String name) {
        this.name = name;
    }

    public Name() {
    }

    public String getValue() {
        return name;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Name name1)) {
            return false;
        }

        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
