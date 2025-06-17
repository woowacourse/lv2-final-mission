package finalmission.apply.infrastructure;

import finalmission.apply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    List<Apply> findAllByPartyId(Long partyId);

    List<Apply> findAllByPlayerId(Long playerId);

    Apply findTop1ByPlayerIdOrderByCreatedAtAsc(Long playerId);
}
