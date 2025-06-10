package finalmission.controller;

import finalmission.domain.Member;
import finalmission.dto.SignUpInfo;
import finalmission.service.MemberService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    MemberService memberService;

    @PostMapping
    ResponseEntity<Member> signUp(SignUpInfo signUpInfo) {
        return ResponseEntity.created(URI.create("/signup"))
                .body(memberService.createMember(signUpInfo));
    }
}
