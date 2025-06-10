package finalmission.party.application;

import finalmission.party.domain.Party;
import finalmission.party.infrastructure.PartyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyFinder {

    private final PartyRepository partyRepository;

    public Party getById(final Long id) {
        return partyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("파티 찾기 실패, 파티 아이디: %d".formatted(id)));
    }
}
