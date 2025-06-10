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
        final Party party = partyFinder.getById(id);
        party.open();
    }

    public void close(final Long id) {
        final Party party = partyFinder.getById(id);
        party.close();
    }

    public void openIfClosed(final Long id) {
        try {
            open(id);
        } catch (final IllegalStateException ignored) {
            // 그럴 수 있지...
        }
    }
}
