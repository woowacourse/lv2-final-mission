package finalmission.player.ui;

import finalmission.player.application.usecase.RegisterPlayerUseCase;
import finalmission.player.application.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignInController {

    private final RegisterPlayerUseCase registerPlayerUseCase;

    public ResponseEntity<String> execute(@RequestBody final SignInRequest signInRequest) {
        registerPlayerUseCase.execute(signInRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 잘 됐습니다");
    }
}
