package finalmission.repository.jpa;

import finalmission.domain.NicknameReservation;
import finalmission.repository.NicknameReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicknameReservationJpaRepository extends JpaRepository<NicknameReservation, Long>,
        NicknameReservationRepository {
}
