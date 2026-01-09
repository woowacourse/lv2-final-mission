package finalmission.presentation.dto;

import finalmission.business.model.entity.Member;
import finalmission.business.model.entity.Reservation;
import java.time.LocalDateTime;

public record ReservationSpec(Member member, String passportId, LocalDateTime departureDateTime,
                              LocalDateTime arrivalDateTime, String departures, String arrivals,
                              String flightCode) {
    public static ReservationSpec of(Reservation reservation) {
        return new ReservationSpec(reservation.getMember(), reservation.getPassportId(),
                reservation.getDepartureDateTime(), reservation.getArrivalDateTime(),
                reservation.getDepartures(), reservation.getArrivals(), reservation.getFlightCode());
    }

    public static ReservationSpec of(Member member, String passportId, LocalDateTime departureDateTime,
                                     LocalDateTime arrivalDateTime, String departures, String arrivals,
                                     String flightCode) {
        return new ReservationSpec(member, passportId, departureDateTime, arrivalDateTime, departures, arrivals,
                flightCode);
    }

}
