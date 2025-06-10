package finalmission.party.application;

import finalmission.party.domain.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyStatusUpdater {

    private final PartyFinder partyFinder;

    public void close(final Long id) {
        final Party party = partyFinder.getById(id);
        party.close();
    }
}
