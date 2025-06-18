package finalmission.concert.service.detail;

import finalmission.common.exception.InvalidInputException;
import finalmission.concert.controller.dto.ConcertResponse;
import finalmission.concert.domain.Concert;
import finalmission.concert.repository.ConcertRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertQueryService {

    private final ConcertRepository concertRepository;

    public Concert get(Long id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException("ID에 해당하는 콘서트를 찾을 수 없습니다."));
    }

    public List<Concert> getAll() {
        return concertRepository.findAll();
    }

    public List<Concert> getAllBefore(final LocalDateTime targetDateTime) {
        return concertRepository.findByConcertDateBefore(targetDateTime);
    }
}
