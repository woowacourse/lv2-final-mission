package finalmission.member.service;

import finalmission.common.security.CookieManager;
import finalmission.common.security.JwtProvider;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRole;
import finalmission.member.dto.LoginRequest;
import finalmission.member.dto.SignupRequest;
import finalmission.member.dto.SignupResponse;
import finalmission.member.repository.MemberRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final JwtProvider jwtProvider;
    private final CookieManager cookieManager;
    private final MemberRepository memberRepository;

    public MemberService(final JwtProvider jwtProvider, final CookieManager cookieManager,
                         final MemberRepository memberRepository) {
        this.jwtProvider = jwtProvider;
        this.cookieManager = cookieManager;
        this.memberRepository = memberRepository;
    }

    public ResponseCookie login(final LoginRequest loginRequest) {
        Member member = memberRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 잘못되었습니다."));
        String jwtToken = jwtProvider.createJwtToken(member);

        return cookieManager.makeCookie("token", jwtToken);
    }

    public SignupResponse signUp(final SignupRequest signupRequest) {
        Member member = new Member(signupRequest.nickname(), signupRequest.email(), signupRequest.password(),
                MemberRole.REGULAR);
        Member savedMember = memberRepository.save(member);
        return new SignupResponse(savedMember.getId());
    }
}
