package finalmission.apply.application;

import finalmission.apply.domain.Apply;
import finalmission.apply.infrastructure.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyCreator {

    private final ApplyRepository applyRepository;

    public Long execute(final Long partyId, final Long playerId) {
        final Apply apply = applyRepository.save(Apply.of(partyId, playerId));
        return apply.getId();
    }
}
