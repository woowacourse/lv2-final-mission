package finalmission.seat.repository.vo;

public record SeatWithReserved(
        Long id,
        Integer seatNumber,
        Boolean isReserved
) {
}
