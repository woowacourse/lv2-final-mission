package finalmission.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Address(
    String street,
    String detail
) {

}
