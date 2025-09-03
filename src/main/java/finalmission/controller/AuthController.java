package finalmission.controller;

import finalmission.domain.Member;
import finalmission.dto.LoginInfo;
import finalmission.dto.request.MemberCreateRequest;
import finalmission.dto.request.MemberSignupRequest;
import finalmission.dto.response.MemberResponse;
import finalmission.service.MemberService;
import finalmission.service.RandommerService;
import finalmission.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RandommerService randommerService;

    public AuthController(final MemberService memberService, final JwtTokenProvider jwtTokenProvider,
            final RandommerService randommerService) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.randommerService = randommerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@RequestBody final MemberSignupRequest request) {
        String name = request.name();
        if (request.name() == null) {
            name = randommerService.generateRandomName();
        }
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest(name, request.email(),
                request.password(), request.role());
        return ResponseEntity.ok(memberService.create(memberCreateRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(final LoginInfo loginInfo, final HttpServletResponse response) {
        final Long id = loginInfo.id();
        final Member member = memberService.find(id);
        final String token = jwtTokenProvider.createToken(member);
        final Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
