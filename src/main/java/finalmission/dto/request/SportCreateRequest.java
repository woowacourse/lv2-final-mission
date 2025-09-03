package finalmission.dto.request;

import finalmission.domain.Sport;

public record SportCreateRequest(
        String name,
        String description,
        Long reservationLimit
) {
    public Sport toDomain() {
        return Sport.withoutId(name, description, reservationLimit);
    }
}
