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

}
