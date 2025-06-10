package finalmission.player.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlayerStatus {

    GO("게임할 준비가 된 전사"),
    STOP("게임을 마친 신사"),
    ;

    private final String description;

    public boolean isGo() {
        return this == GO;
    }

    public boolean isStop() {
        return this == STOP;
    }
}
