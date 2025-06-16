package finalmission.auth.service;

import finalmission.auth.controller.dto.request.LoginMember;
import finalmission.auth.controller.dto.request.LoginRequest;
import finalmission.auth.infrastructure.JwtTokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.repository.JpaMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JpaMemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final JpaMemberRepository memberRepository, final JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(final LoginRequest request) {
        Member member = memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 사용자가 없습니다."));
        return jwtTokenProvider.createToken(member);
    }

    public LoginMember findLoginMemberByToken(final String token) {
        Long id = jwtTokenProvider.getSubjectFromToken(token);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new LoginMember(member.getId(), member.getEmail(), member.getName());
    }
}
