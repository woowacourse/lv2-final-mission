package finalmission.concert.repository;

import finalmission.concert.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaConcertRepository extends JpaRepository<Concert, Long>, ConcertRepository {

}
