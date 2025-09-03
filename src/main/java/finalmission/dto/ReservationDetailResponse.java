package finalmission.dto;

import finalmission.domain.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public record ReservationDetailResponse(Long reservationId,
                                        String title,
                                        String description,
                                        String manager,
                                        String phoneNumber,
                                        LocalDate date,
                                        LocalTime time) {

    public static ReservationDetailResponse from(Reservation reservation) {
        return new ReservationDetailResponse(
                reservation.getId(),
                reservation.getTour().getTitle(),
                reservation.getTour().getDescription(),
                reservation.getManager().getName(),
                reservation.getManager().getPhoneNumber(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public static List<ReservationDetailResponse> from(Reservation... reservations) {
        return Arrays.stream(reservations)
                .map(ReservationDetailResponse::from)
                .toList();
    }
}
