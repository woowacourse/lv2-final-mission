package finalmission.party.application;

import finalmission.party.domain.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyStatusUpdater {

    private final PartyFinder partyFinder;

    public void open(final Long id) {
        getParty(id).open();
    }

    public void close(final Long id) {
        getParty(id).close();
    }

    private Party getParty(final Long id) {
        return partyFinder.getById(id);
    }
}
