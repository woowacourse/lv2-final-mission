package finalmission.reservation.application;

import finalmission.exception.resource.AlreadyExistException;
import finalmission.exception.resource.ResourceInUseException;
import finalmission.reservation.domain.ReservationTime;
import finalmission.reservation.domain.ReservationTimeRepository;
import finalmission.reservation.ui.dto.CreateReservationTimeRequest;
import finalmission.reservation.ui.dto.ReservationTimeResponse;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationTimeResponse create(final CreateReservationTimeRequest request) {
        final LocalTime startAt = request.startAt();
        final List<ReservationTime> founds = reservationTimeRepository.findAllByStartAt(startAt);
        if (!founds.isEmpty()) {
            throw new AlreadyExistException("해당 예약 시간이 이미 존재합니다. startAt = " + startAt);
        }

        final ReservationTime found = reservationTimeRepository.save(new ReservationTime(startAt));

        return ReservationTimeResponse.from(found);
    }

    @Transactional
    public void deleteById(final Long timeId) {
        reservationTimeRepository.getById(timeId);

        try {
            reservationTimeRepository.deleteById(timeId);
        } catch (final DataIntegrityViolationException e) {
            throw new ResourceInUseException("해당 예약 시간을 사용하고 있는 예약이 존재합니다. id = " + timeId);
        }
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponse> findAll() {
        final List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }
}
