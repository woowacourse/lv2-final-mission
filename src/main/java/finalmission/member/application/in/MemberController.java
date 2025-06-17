package finalmission.member.application.in;

import finalmission.member.application.MemberService;
import finalmission.member.application.in.dto.SignupMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 API")
@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입 API")
    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody SignupMember request) {
        memberService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
