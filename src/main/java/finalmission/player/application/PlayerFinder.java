package finalmission.player.application;

import finalmission.player.infrastructure.PlayerRepository;
import finalmission.player.domain.Player;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerFinder {

    private final PlayerRepository playerRepository;

    public Player getById(final Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("플레이어 찾기 실패, 플레이어 아이디: %d".formatted(id)));
    }

    public Player getByNickname(final String nickname) {
        return playerRepository.findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException("플레이어 찾기 실패, 플레이어 닉네임: %s".formatted(nickname)));
    }

    public Long getIdByNickname(final String nickname) {
        return playerRepository.findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException("플레이어 찾기 실패, 플레이어 닉네임: %s".formatted(nickname)))
                .getId();
    }

    public boolean existsByNickname(final String nickname) {
        return playerRepository.existsByNickname(nickname);
    }

    public List<Player> getAllByIds(final List<Long> ids) {
        return playerRepository.findAllById(ids);
    }
}
