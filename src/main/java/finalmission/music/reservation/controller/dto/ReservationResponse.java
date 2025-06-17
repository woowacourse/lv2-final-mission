package finalmission.music.reservation.controller.dto;

import finalmission.music.reservation.domain.Reservation;

public record ReservationResponse(Long id, String memberName, Long lotteryId, String address) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getMember().getName(),
            reservation.getLottery().getId(),
            reservation.getAddress());
    }
}
