package finalmission.controller;

import finalmission.domain.LolName;
import finalmission.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody final MemberSignUpRequest request
    ) {
        memberService.create(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(
            @RequestParam("playerName") final String playerName,
            @RequestParam("playerTag") final String playerTag,
            @RequestParam("password") final String password
    ) {
        final LolName lolName = new LolName(playerName, playerTag);
        final MemberLoginResponse response = memberService.login(lolName, password);

        return ResponseEntity.ok(response);
    }
}
