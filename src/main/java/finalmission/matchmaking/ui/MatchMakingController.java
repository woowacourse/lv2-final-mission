package finalmission.matchmaking.ui;

import finalmission.apply.domain.Apply;
import finalmission.matchmaking.application.JoinMatchMakingUseCase;
import finalmission.matchmaking.application.ExitMatchMakingUseCase;
import finalmission.matchmaking.application.GetMatchMakingUseCase;
import finalmission.player.application.usecase.ValidatePlayerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class MatchMakingController {

    private final GetMatchMakingUseCase getMatchMakingUseCase;
    private final JoinMatchMakingUseCase joinMatchMakingUseCase;
    private final ExitMatchMakingUseCase exitMatchMakingUseCase;
    private final ValidatePlayerUseCase validatePlayerUseCase;

    @GetMapping
    public ResponseEntity<List<Apply>> get(@RequestBody final UserInfo userInfo) {
        validatePlayerUseCase.execute(userInfo);
        final List<Apply> result = getMatchMakingUseCase.execute(userInfo.nickname());

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody final UserInfo userInfo) {
        validatePlayerUseCase.execute(userInfo);
        joinMatchMakingUseCase.execute(userInfo.nickname());

        return ResponseEntity.status(HttpStatus.CREATED).body("신청 성공");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody final UserInfo userInfo) {
        validatePlayerUseCase.execute(userInfo);
        exitMatchMakingUseCase.execute(userInfo.nickname());

        return ResponseEntity.ok().body("삭제 완료");
    }
}
