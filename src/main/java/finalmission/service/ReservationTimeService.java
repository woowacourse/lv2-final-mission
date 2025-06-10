package finalmission.service;

import finalmission.dto.request.CreateReservationTimeRequest;
import finalmission.dto.response.CreateReservationTimeResponse;
import finalmission.dto.response.ReservationTimeResponse;
import finalmission.entity.ReservationTime;
import finalmission.exception.custom.CannotRemoveException;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.NotExistedValueException;
import finalmission.repository.ReservationRepository;
import finalmission.repository.ReservationTimeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository,
                                  final ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationTimeResponse> findAllReservationTime() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public CreateReservationTimeResponse addReservationTime(final CreateReservationTimeRequest request) {
        if (reservationTimeRepository.existsByStartAt(request.startAt())) {
            throw new DuplicatedValueException("이미 존재하는 예약 가능 시간입니다.");
        }
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return CreateReservationTimeResponse.from(saved);
    }

    public void deleteReservationTime(final Long id) {
        if (reservationRepository.existsByReservationTimeId(id)) {
            throw new CannotRemoveException("예약이 존재하는 예약 가능 시간은 제거할 수 없습니다.");
        }
        if (!reservationTimeRepository.existsById(id)) {
            throw new NotExistedValueException("존재하지 않는 예약 가능 시간입니다.");
        }
        reservationTimeRepository.deleteById(id);
    }
}
