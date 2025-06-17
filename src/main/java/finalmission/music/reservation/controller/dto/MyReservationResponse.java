package finalmission.music.reservation.controller.dto;

import finalmission.music.reservation.domain.Reservation;
import finalmission.music.lottery.controller.dto.LotteryResponse;

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
