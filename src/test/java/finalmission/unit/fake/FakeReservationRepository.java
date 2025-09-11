package finalmission.unit.fake;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.infrastructure.ReservationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation.getDate(),
                reservation.getStartAt(),
                reservation.getEndAt(), reservation.getMember(), reservation.getToilet());
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public List<Reservation> findByMemberId(Long memberId) {
        return reservations.stream()
                .filter(reservation -> reservation.getMember().getId().equals(memberId))
                .toList();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    @Override
    public List<Reservation> findByToiletIdAndDate(Long toiletId, LocalDate date) {
        return reservations.stream()
                .filter(reservation -> toiletId == null || reservation.getToilet().getId().equals(toiletId))
                .filter(reservation -> date == null || reservation.getDate().equals(date))
                .toList();
    }
}
