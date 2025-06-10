package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Ticket(
    @Column(nullable = false)
    String identifier
) {

}
