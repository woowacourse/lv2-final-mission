package finalmission.matchmaking.application;

import finalmission.apply.application.ApplyDeleter;
import finalmission.apply.application.ApplyFinder;
import finalmission.apply.domain.Apply;
import finalmission.party.application.PartyStatusUpdater;
import finalmission.player.application.PlayerFinder;
import finalmission.player.application.PlayerStatusUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExitMatchMakingUseCase {

    private final PlayerFinder playerFinder;
    private final ApplyFinder applyFinder;
    private final ApplyDeleter applyDeleter;
    private final PlayerStatusUpdater playerStatusUpdater;
    private final PartyStatusUpdater partyStatusUpdater;

    public void execute(final String nickname) {
        final Long playerId = playerFinder.getIdByNickname(nickname);
        final Apply target = applyFinder.getNewestByPlayerId(playerId);
        applyDeleter.execute(target);

        playerStatusUpdater.stop(playerId);
        final Long partyId = target.getPartyId();
        partyStatusUpdater.open(partyId);
    }
}
