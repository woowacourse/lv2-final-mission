package finalmission.concert.service.detail;

import finalmission.concert.domain.Concert;
import finalmission.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertCommandService {

    private final ConcertRepository concertRepository;

    public Concert create(final Concert concert) {
        return concertRepository.save(concert);
    }
}
