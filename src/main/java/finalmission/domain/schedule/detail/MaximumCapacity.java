package finalmission.domain.schedule.detail;

import jakarta.persistence.Embeddable;

@Embeddable
public class MaximumCapacity {

    private int maximumCapacity;

    public MaximumCapacity(final int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    protected MaximumCapacity() {
    }

    public int getValue() {
        return maximumCapacity;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final MaximumCapacity count1)) {
            return false;
        }

        return maximumCapacity == count1.maximumCapacity;
    }

    @Override
    public int hashCode() {
        return maximumCapacity;
    }
}
