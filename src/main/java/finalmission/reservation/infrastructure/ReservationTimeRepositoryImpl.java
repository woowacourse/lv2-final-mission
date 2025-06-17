package finalmission.reservation.infrastructure;

import finalmission.exception.resource.ResourceNotFoundException;
import finalmission.reservation.domain.ReservationTime;
import finalmission.reservation.domain.ReservationTimeRepository;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JpaReservationTimeRepository jpaReservationTimeRepository;

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        return jpaReservationTimeRepository.save(reservationTime);
    }

    @Override
    public void deleteById(final Long timeId) {
        jpaReservationTimeRepository.deleteById(timeId);
    }

    @Override
    public ReservationTime getById(final Long timeId) {
        return jpaReservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 예약 시간이 존재하지 않습니다. id = " + timeId));
    }

    @Override
    public List<ReservationTime> findAllByStartAt(final LocalTime startAt) {
        return jpaReservationTimeRepository.findAllByStartAt(startAt);
    }

    @Override
    public List<ReservationTime> findAll() {
        return jpaReservationTimeRepository.findAll();
    }
}
