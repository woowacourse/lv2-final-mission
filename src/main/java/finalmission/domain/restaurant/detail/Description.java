package finalmission.domain.restaurant.detail;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Description {

    private String description;

    public Description(final String description) {
        this.description = description;
    }

    protected Description() {
    }

    public String getValue() {
        return description;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Description that)) {
            return false;
        }

        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }
}
