package finalmission.concert.repository;

import finalmission.concert.controller.dto.ConcertResponse;
import finalmission.concert.domain.Concert;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertRepository {

    Concert save(Concert concert);

    Optional<Concert> findById(Long id);

    List<Concert> findAll();

    List<Concert> findByConcertDateBefore(LocalDateTime targetDateTime);
}
