package finalmission.member.controller;

import finalmission.member.controller.dto.request.MemberRequest;
import finalmission.member.controller.dto.response.MemberResponse;
import finalmission.member.domain.Member;
import finalmission.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody @Valid MemberRequest request) {
        Member member = memberService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(member));
    }

}
