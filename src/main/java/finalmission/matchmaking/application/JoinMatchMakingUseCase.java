package finalmission.matchmaking.application;

import finalmission.apply.application.ApplyCounter;
import finalmission.apply.application.ApplyCreator;
import finalmission.party.application.PartyStatusUpdater;
import finalmission.party.application.VacancyGetter;
import finalmission.player.application.PlayerFinder;
import finalmission.player.application.PlayerStatusUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinMatchMakingUseCase {

    private final PlayerFinder playerFinder;
    private final VacancyGetter vacancyGetter;
    private final ApplyCreator applyCreator;
    private final PlayerStatusUpdater playerStatusUpdater;
    private final PartyStatusUpdater partyStatusUpdater;
    private final ApplyCounter applyCounter;

    public void execute(final String nickname) {
        final Long playerId = playerFinder.getIdByNickname(nickname);

        final Long partyId = vacancyGetter.execute();
        applyCreator.execute(partyId, playerId);
        playerStatusUpdater.go(playerId);

        final int count = applyCounter.executeByPartyId(partyId);
        if (count == 4) {
            partyStatusUpdater.close(partyId);
            // TODO 외부 API로 연락 보내면 된다, 팀원 닉네임을 주면 될 듯

        }
        if (count > 5) {
            throw new IllegalStateException("있을 수 없는 일이 일어난거야");
        }
    }
}
