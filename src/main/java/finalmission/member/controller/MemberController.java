package finalmission.member.controller;

import finalmission.member.dto.MemberResponse;
import finalmission.member.dto.MemberResult;
import finalmission.member.dto.RegisterMemberRequest;
import finalmission.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> register(@RequestBody RegisterMemberRequest memberRequest) {
        MemberResult result = memberService.register(memberRequest);
        MemberResponse memberResponse = new MemberResponse(result.id(), result.name(), result.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse);
    }
}
