package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record Id(
        @Column(name = "id")
        String value
) {
    public static Id random() {
        String randomValue = UUID.randomUUID().toString().substring(0, 8);
        return new Id(randomValue);
    }
}
