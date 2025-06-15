package finalmission.member.presentation;

import finalmission.member.dto.request.MemberRequest;
import finalmission.member.dto.response.MemberResponse;
import finalmission.member.dto.response.NicknameResponse;
import finalmission.member.service.MemberService;
import jakarta.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<MemberResponse> signUp(@RequestBody @Valid MemberRequest request) {
        MemberResponse response = memberService.createNewMember(request);
        return ResponseEntity.created(URI.create("/members/" + response.id())).body(response);
    }

    @GetMapping("/nickname-suggestion")
    public ResponseEntity<NicknameResponse> suggestNickname() {
        NicknameResponse response = memberService.suggestNickname();
        return ResponseEntity.ok(response);
    }
}
