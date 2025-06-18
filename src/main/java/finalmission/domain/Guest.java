package finalmission.domain;

import finalmission.exception.InvalidGuestSizeException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Guest {
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 14;
    private int size;

    public Guest(final int size) {
        validateSize(size);
        this.size = size;
    }

    public Guest() {
    }

    private void validateSize(final int size) {
        if (isNotAllowedSize(size)) {
            throw new InvalidGuestSizeException();
        }
    }

    private boolean isNotAllowedSize(final int size) {
        return size < MIN_SIZE || size > MAX_SIZE;
    }


}
