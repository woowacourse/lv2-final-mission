package finalmission.member.controller;

import finalmission.member.dto.MemberCreateRequest;
import finalmission.member.dto.MemberCreateResponse;
import finalmission.member.service.MemberService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberCreateResponse> create(@RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = memberService.create(request);
        return ResponseEntity.created(URI.create("/members/" + response.id())).body(response);
    }
}
