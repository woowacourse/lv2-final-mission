package finalmission.reservationtime.service;

import finalmission.reservation.infrastructure.ReservationRepository;
import finalmission.reservationtime.domain.ReservationTime;
import finalmission.reservationtime.dto.ReservationTimeRequest;
import finalmission.reservationtime.dto.ReservationTimeResponse;
import finalmission.reservationtime.infrastructure.ReservationTimeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository, ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @Transactional
    public ReservationTimeResponse save(ReservationTimeRequest request) {
        if (reservationTimeRepository.existsByStartAt(request.startAt())) {
            throw new IllegalArgumentException("이미 존재하는 예약 시간입니다.");
        }
        ReservationTime reservationTime = ReservationTime.createWithoutId(request.startAt());
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    @Transactional
    public void delete(Long id) {
        if (reservationRepository.existsByTimeId(id)) {
            throw new IllegalArgumentException("예약 중인 시간입니다.");
        }
        reservationTimeRepository.deleteById(id);
    }
}
