package finalmission.matchmaking.ui;

import finalmission.apply.domain.Apply;
import finalmission.matchmaking.application.JoinMatchMakingUseCase;
import finalmission.matchmaking.application.ExitMatchMakingUseCase;
import finalmission.matchmaking.application.GetMatchMakingUseCase;
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

    // TODO validation Password

    @GetMapping
    public ResponseEntity<List<Apply>> get(@RequestBody final MatchMakingRequest matchMakingRequest) {
        final List<Apply> result = getMatchMakingUseCase.execute(matchMakingRequest.nickname());

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody final MatchMakingRequest matchMakingRequest) {
        joinMatchMakingUseCase.execute(matchMakingRequest.nickname());

        return ResponseEntity.status(HttpStatus.CREATED).body("신청 성공");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody final MatchMakingRequest matchMakingRequest) {
        exitMatchMakingUseCase.execute(matchMakingRequest.nickname());

        return ResponseEntity.ok().body("삭제 완료");
    }
}
