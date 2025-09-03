package finalmission.service;

import finalmission.domain.ReservationTime;
import finalmission.dto.request.ReservationTimeCreateRequest;
import finalmission.dto.response.ReservationTimeResponse;
import finalmission.exception.InvalidRequestException;
import finalmission.exception.NotFoundException;
import finalmission.repository.ReservationRepository;
import finalmission.repository.ReservationTimeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository,
            final ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationTimeResponse create(final ReservationTimeCreateRequest request) {
        final ReservationTime savedReservationTime = reservationTimeRepository.save(request.toDomain());
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public void removeReservationTime(final Long id) {
        if (!reservationTimeRepository.existsById(id)) {
            throw new NotFoundException();
        }

        if (reservationRepository.existsByTimeId(id)) {
            throw new InvalidRequestException("예약이 존재하고 있어 삭제할 수 없습니다");
        }

        reservationTimeRepository.deleteById(id);
    }

    public List<ReservationTimeResponse> getAll() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTime find(final Long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

}
