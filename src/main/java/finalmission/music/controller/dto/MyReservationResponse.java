package finalmission.music.controller.dto;

import finalmission.music.domain.Reservation;

public record MyReservationResponse(
    LotteryResponse lotteryResponse,
    String member,
    String address
) {
    public static MyReservationResponse from(Reservation reservation) {
        return new MyReservationResponse(
            LotteryResponse.from(reservation.getLottery()),
            reservation.getMember().getName(),
            reservation.getAddress()
        );
    }
}
