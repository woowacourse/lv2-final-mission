package finalmission.facade.application;

import finalmission.apply.application.ApplyFinder;
import finalmission.apply.domain.Apply;
import finalmission.player.application.PlayerFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetApplyPartyUseCase {

    private final PlayerFinder playerFinder;
    private final ApplyFinder applyFinder;

    public List<Apply> execute(final String nickname) {
        final Long playerId = playerFinder.getIdByNickname(nickname);

        return applyFinder.getAllByPlayerId(playerId);
    }
}
