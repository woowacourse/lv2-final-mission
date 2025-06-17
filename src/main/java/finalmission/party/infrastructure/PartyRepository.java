package finalmission.party.infrastructure;

import finalmission.party.domain.Party;
import finalmission.party.domain.PartyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findTop1ByPartyStatusOrderByCreatedAtAsc(PartyStatus partyStatus);
}
