package finalmission.concert.controller.dto;

import java.time.LocalDateTime;

public record ConcertResponse(
        Long id,
        String title,
        String artist,
        LocalDateTime concertDate,
        Long venueId,
        Long price,
        String description
) {
}
