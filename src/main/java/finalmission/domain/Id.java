package finalmission.domain;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record Id(
        String value
) {
    public static Id random() {
        String randomValue = UUID.randomUUID().toString().substring(0, 8);
        return new Id(randomValue);
    }
}
