package finalmission.member.presentation;

import finalmission.member.dto.MemberRegistrationRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> register(@RequestBody MemberRegistrationRequest request) {
        MemberResponse response = memberService.registerMember(request);
        URI uri = URI.create("member/" + response.memberId());
        return ResponseEntity.created(uri).body(response);
    }
}
