package finalmission.presentation.controller;

import finalmission.application.MemberService;
import finalmission.domain.Member;
import finalmission.presentation.dto.MemberCreateRequest;
import finalmission.presentation.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<MemberResponse> register(@RequestBody MemberCreateRequest request) {
        Member member = memberService.register(request.email(), request.password());
        return ResponseEntity.ok(MemberResponse.from(member));
    }
}
