package finalmission.facade.application;

import finalmission.apply.application.ApplyCounter;
import finalmission.apply.application.ApplyCreator;
import finalmission.party.application.PartyStatusUpdater;
import finalmission.player.application.PlayerFinder;
import finalmission.player.application.PlayerStatusUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateApplyPartyUseCase {

    private final PlayerFinder playerFinder;
    private final VacancyGetter vacancyGetter;
    private final ApplyCreator applyCreator;
    private final PlayerStatusUpdater playerStatusUpdater;
    private final PartyStatusUpdater partyStatusUpdater;
    private final ApplyCounter applyCounter;

    public void execute(final String nickname) {
        final Long playerId = playerFinder.getIdByNickname(nickname);

        final Long partyId = vacancyGetter.execute();
        final Long applyId = applyCreator.execute(partyId, playerId);
        playerStatusUpdater.go(playerId);

        final int count = applyCounter.executeByPartyId(partyId);
        if (count == 4) {
            partyStatusUpdater.close(partyId);
            // TODO 외부 API로 연락 보내면 된다
        }
        if (count > 5) {
            throw new IllegalStateException("있을 수 없는 일이 일어난거야");
        }
    }
}
