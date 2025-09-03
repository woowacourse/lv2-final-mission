package finalmission.service;

import finalmission.domain.Sport;
import finalmission.dto.request.SportCreateRequest;
import finalmission.dto.response.SportResponse;
import finalmission.exception.InvalidRequestException;
import finalmission.exception.NotFoundException;
import finalmission.repository.ReservationRepository;
import finalmission.repository.SportRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SportService {
    private final SportRepository sportRepository;
    private final ReservationRepository reservationRepository;

    public SportService(final SportRepository sportRepository, final ReservationRepository reservationRepository) {
        this.sportRepository = sportRepository;
        this.reservationRepository = reservationRepository;
    }

    public SportResponse create(final SportCreateRequest request) {
        final Sport savedSport = sportRepository.save(request.toDomain());
        return SportResponse.from(savedSport);
    }

    public void removeSport(final Long id) {
        if (!sportRepository.existsById(id)) {
            throw new NotFoundException();
        }

        if (reservationRepository.existsBySportId(id)) {
            throw new InvalidRequestException("예약이 이미 존재하고 있어 삭제할 수 없습니다.");
        }

        sportRepository.deleteById(id);
    }

    public List<SportResponse> getAll() {
        return sportRepository.findAll().stream()
                .map(SportResponse::from)
                .toList();
    }

    public Sport find(final Long id) {
        return sportRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

}
