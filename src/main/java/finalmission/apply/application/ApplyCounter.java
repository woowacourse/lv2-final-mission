package finalmission.apply.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyCounter {

    public final ApplyFinder applyFinder;

    public int executeByPartyId(final Long partyId) {
        return applyFinder.getAllByPartyId(partyId).size();
    }
}
