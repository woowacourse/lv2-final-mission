package finalmission.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    // 실제 프로덕션에서 인증 서비스를 활용하면, 인증 키도 요청으로 받아야 함.
    public ResponseEntity<Void> createMember(@RequestBody String phoneNumber) {
        memberService.createMember(phoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
