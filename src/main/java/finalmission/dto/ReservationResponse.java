package finalmission.dto;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationTimeSlot;

public record ReservationResponse(
        long id,
        String email,
        int startTime,
        int endTime,
        int numberOfPeople
) {

    public static ReservationResponse from(final Reservation reservation) {
        final ReservationTimeSlot reservationTimeSlot = reservation.getReservationTimeSlot();
        final Member member = reservation.getMember();
        return new ReservationResponse(
                reservation.getId(),
                member.getEmail(),
                reservationTimeSlot.getStartTime(),
                reservationTimeSlot.getEndTime(),
                reservation.getNumberOfPeople()
        );
    }
}
