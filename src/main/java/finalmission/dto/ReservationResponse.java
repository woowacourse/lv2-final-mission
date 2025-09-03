package finalmission.dto;

import finalmission.domain.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public record ReservationResponse(Long reservationId,
                                  String title,
                                  String manager,
                                  String phoneNumber,
                                  LocalDate date,
                                  LocalTime time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getTour().getTitle(),
                reservation.getManager().getName(),
                reservation.getManager().getPhoneNumber(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public static List<ReservationResponse> from(Reservation... reservations) {
        return Arrays.stream(reservations)
                .map(ReservationResponse::from)
                .toList();
    }
}
