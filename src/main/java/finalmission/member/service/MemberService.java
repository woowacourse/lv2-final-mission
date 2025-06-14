package finalmission.member.service;

import finalmission.auth.JwtTokenHandler;
import finalmission.member.domain.Member;
import finalmission.member.domain.vo.Email;
import finalmission.member.domain.vo.Password;
import finalmission.member.repository.MemberRepository;
import finalmission.member.service.dto.CreateMemberRequest;
import finalmission.member.service.dto.LoginRequest;
import finalmission.member.service.dto.LoginResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenHandler jwtTokenHandler;

    public MemberService(MemberRepository memberRepository, JwtTokenHandler jwtTokenHandler) {
        this.memberRepository = memberRepository;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Transactional
    public void create(CreateMemberRequest request) {
        Member member = request.toEntity();

        validateDuplicated(member);
        memberRepository.save(member);
    }

    private void validateDuplicated(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("이미 가입한 이메일 입니다.");
        }
    }

    public LoginResponse login(LoginRequest request) {
        Email email = new Email(request.email());
        Password password = new Password(request.password());

        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 정보입니다."));

        return new LoginResponse(jwtTokenHandler.createToken(member));
    }
}
