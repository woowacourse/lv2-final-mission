package finalmission.concert.controller.dto;

import java.time.LocalDateTime;

public record ConcertRequest(
        String title,
        String artist,
        LocalDateTime concertDate,
        Long venueId,
        Long price,
        String description
) {
}
