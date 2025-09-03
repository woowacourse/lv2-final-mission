package finalmission.running.repository;

import finalmission.running.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
