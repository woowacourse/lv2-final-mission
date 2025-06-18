package finalmission.seat.controller.dto;

public record SeatResponse(
        Long id,
        Integer seatNumber,
        Long venueId
) {
}
