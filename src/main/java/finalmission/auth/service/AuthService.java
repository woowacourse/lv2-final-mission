package finalmission.auth.service;

import finalmission.auth.dto.LoginRequest;
import finalmission.auth.exception.AuthException;
import finalmission.external.jwt.JwtTokenProvider;
import finalmission.member.domian.Member;
import finalmission.member.domian.Role;
import finalmission.member.dto.LoginMember;
import finalmission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public Role findRoleByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthException("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }
        return jwtTokenProvider.getRole(token);
    }

    public Member findMemberByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthException("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }

        Long memberId = jwtTokenProvider.getId(token);
        return getMember(memberId);
    }

    public String login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email());
        if (!member.isCorrectPassword(loginRequest.password())) {
            throw new IllegalArgumentException("비밀번호가 유효하지 않습니다.");
        }
        return jwtTokenProvider.createToken(new LoginMember(member.getId(), member.getName(), member.getRole()));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 멤버는 존재하지 않는 회원입니다."));
    }
}
