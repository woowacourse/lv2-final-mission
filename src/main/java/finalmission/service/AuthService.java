package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.MemberRole;
import finalmission.dto.request.LoginRequest;
import finalmission.dto.response.MemberResponse;
import finalmission.infra.auth.LoginMember;
import finalmission.infra.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginRequest loginRequest) {
        MemberResponse memberResponse = memberService.findByEmail(loginRequest.email());
        Member member = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.password(), memberResponse.memberRole());
        if (member.isSamePassword(loginRequest.password())) {
            return jwtTokenProvider.createToken(
                    new LoginMember(member.getId(), member.getEmail(), member.getName(), member.getMemberRole()));
        }
        throw new IllegalArgumentException("[ERROR] 올바르지 않은 비밀번호입니다.");
    }

    public LoginMember findMemberByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 토큰입니다.");
        }
        Long memberId = jwtTokenProvider.getId(token);
        MemberResponse memberResponse = memberService.findById(memberId);
        return new LoginMember(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
    }

    public MemberRole findRoleByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 토큰입니다.");
        }
        return jwtTokenProvider.getRole(token);
    }
}
