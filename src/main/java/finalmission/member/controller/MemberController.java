package finalmission.member.controller;

import finalmission.global.auth.annotation.RoleRequired;
import finalmission.member.dto.request.MemberCreateRequest;
import finalmission.member.dto.response.MemberResponse;
import finalmission.member.entity.RoleType;
import finalmission.member.service.MemberService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<MemberResponse> createMember(
            @RequestBody MemberCreateRequest request
    ) {
        MemberResponse response = memberService.createMember(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    @RoleRequired(roleType = RoleType.ADMIN)
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        List<MemberResponse> responses = memberService.getAllMembers();
        return ResponseEntity.ok().body(responses);
    }
}
