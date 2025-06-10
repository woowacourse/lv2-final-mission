package finalmission.apply.application;

import finalmission.apply.domain.Apply;
import finalmission.apply.infrastructure.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyFinder {

    private final ApplyRepository applyRepository;

    public List<Apply> getAllByPartyId(final Long partyId) {
        return applyRepository.findAllByPartyId(partyId);
    }

    public List<Apply> getAllByPlayerId(final Long playerId) {
        return applyRepository.findAllByPlayerId(playerId);
    }

    public Apply getNewestByPlayerId(final Long playerId) {
        return applyRepository.findTop1ByPlayerIdOrderByCreatedAtAsc(playerId);
    }
}
