package finalmission.seat.controller.dto;

public record SeatRequest(
        Integer seatNumber,
        Long venueId
) {
}
