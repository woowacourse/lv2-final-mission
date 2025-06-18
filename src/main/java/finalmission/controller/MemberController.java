package finalmission.controller;

import finalmission.domain.Member;
import finalmission.dto.request.SignUpRequest;
import finalmission.dto.response.SignUpResponse;
import finalmission.service.MemberService;
import finalmission.service.RandommerService;
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
    private final RandommerService randommerService;

    public MemberController(final MemberService memberService, final RandommerService randommerService) {
        this.memberService = memberService;
        this.randommerService = randommerService;
    }

    @PostMapping
    public ResponseEntity<SignUpResponse> createMember(@RequestBody SignUpRequest request) {
        String randomName = randommerService.generateRandomFirstName();
        Member member = memberService.signUp(request, randomName);
        SignUpResponse response = SignUpResponse.from(member);

        return ResponseEntity.created(URI.create("/api/members/" + member.getId())).body(response);
    }
}
