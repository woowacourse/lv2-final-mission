package finalmission.member.presentation;

import static finalmission.member.presentation.MemberController.MEMBER_BASE_URL;

import finalmission.member.dto.SignupRequest;
import finalmission.member.dto.SignupResponse;
import finalmission.member.service.MemberService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MEMBER_BASE_URL)
public class MemberController {


    public static final String MEMBER_BASE_URL = "/members";
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<SignupResponse>> getMembers() {
        List<SignupResponse> response = memberService.getMembers();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = memberService.createUser(request);
        URI uri = URI.create(MEMBER_BASE_URL + "/" + response.id());
        return ResponseEntity.created(uri).body(response);
    }

}
