package finalmission.dto.response;

import finalmission.domain.Sport;

public record SportResponse(
        Long id,
        String name,
        String description,
        String reservationLimit
) {
    public static SportResponse from(final Sport sport) {
        return new SportResponse(sport.getId(), sport.getName(), sport.getDescription(), sport.getDescription());
    }
}
