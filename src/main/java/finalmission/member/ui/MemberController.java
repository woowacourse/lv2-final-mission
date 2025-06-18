package finalmission.member.ui;

import finalmission.auth.jwt.JwtTokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.member.ui.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(MemberController.BASE_PATH)
public class MemberController {

    public static final String BASE_PATH = "/members";

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인")
    public String signIn(@RequestBody LoginRequest request,
                         HttpServletResponse response) {
        Member member = memberRepository.signIn(request);

        String token = jwtTokenProvider.createToken(member);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        return member.getName();
    }
}
