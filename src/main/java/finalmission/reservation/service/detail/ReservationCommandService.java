package finalmission.reservation.service.detail;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.seat.domain.Seat;
import finalmission.seat.service.detail.SeatQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationCommandService {

    private final SeatQueryService seatQueryService;
    private final ReservationRepository reservationRepository;

    public Reservation create(final Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation update(final Reservation reservation, final Integer seatNumber) {
        final Seat seat = seatQueryService.getByVenueAndSeatNumber(reservation.getConcert().getVenue(),
                seatNumber);

        reservation.changeSeat(seat);
        return reservationRepository.save(reservation);
    }

    public void delete(final Reservation reservation) {
        reservationRepository.delete(reservation);
    }
}
