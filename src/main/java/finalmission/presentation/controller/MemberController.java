package finalmission.presentation.controller;

import finalmission.dto.request.MemberRequest;
import finalmission.dto.response.MemberResponse;
import finalmission.service.MemberService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> signUp(@RequestBody MemberRequest request) {
        MemberResponse response = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/api/members/" + response.id())).body(response);
    }
}
