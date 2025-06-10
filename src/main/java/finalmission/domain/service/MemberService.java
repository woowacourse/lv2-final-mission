package finalmission.domain.service;

import finalmission.domain.entity.Member;
import finalmission.repository.MemberRepository;
import finalmission.domain.service.dto.LoginRequest;
import finalmission.domain.service.dto.SignUpRequest;
import finalmission.infrastructure.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void registerMember(SignUpRequest request) {
        Member member = Member.createWithoutId(request.name(), request.email(), request.password());

        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이메일은 중복될 수 없습니다.");
        }
        memberRepository.save(member);
    }

    public String publishLoginToken(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이메일이나 비밀번호가 올바르지 않습니다."));
        if (!member.matchPassword(password)) {
            throw new IllegalArgumentException("[ERROR] 이메일이나 비밀번호가 올바르지 않습니다.");
        }
        return jwtTokenProvider.createToken(member);
    }
}
