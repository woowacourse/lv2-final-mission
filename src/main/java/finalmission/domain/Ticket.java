package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Ticket(
    @Column(nullable = false)
    String identifier
) {

    public Ticket {
        validateNonNull(identifier);
    }

    private void validateNonNull(final String identifier) {
        if (identifier == null || identifier.isBlank()) {
            throw new IllegalArgumentException("식별자는 필수입니다.");
        }
    }
}
