package finalmission.service;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateTokenRequest;
import finalmission.dto.request.LoginRequest;
import finalmission.dto.request.SignUpRequest;
import finalmission.dto.response.SignUpResponse;
import finalmission.entity.Member;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.UnauthorizedException;
import finalmission.jwt.JwtTokenProvider;
import finalmission.repository.MemberRepository;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(final MemberRepository memberRepository, final JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(final LoginRequest request) {
        Member member = memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new UnauthorizedException("유효하지 않은 이메일 또는 패스워드입니다."));
        return jwtTokenProvider.createTokenByMember(new CreateTokenRequest(member, new Date()));
    }

    public SignUpResponse signup(final SignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicatedValueException("이미 사용 중인 이메일입니다.");
        }
        Member member = new Member(request.email(), request.password(), request.name(), MemberRole.MEMBER);
        Member saved = memberRepository.save(member);
        return SignUpResponse.from(saved);
    }
}
