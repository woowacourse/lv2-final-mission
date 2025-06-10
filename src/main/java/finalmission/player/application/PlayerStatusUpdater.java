package finalmission.player.application;

import finalmission.player.domain.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerStatusUpdater {

    private final PlayerFinder playerFinder;

    @Transactional
    public void go(final Long id) {
        final Player player = playerFinder.getById(id);
        player.go();
    }

    public void stop(final Long id) {
        final Player player = playerFinder.getById(id);
        player.stop();
    }
}
