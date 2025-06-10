package finalmission.member.service;

import finalmission.login.authorization.JwtTokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.domain.Role;
import finalmission.member.dto.MemberLoginRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.dto.MemberTokenResponse;
import finalmission.member.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //
    public MemberResponse findByToken(final String token) {
        String email = jwtTokenProvider.getPayloadEmail(token);
        return findByEmail(email);
    }

    private MemberResponse findByEmail(final String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일이 올바르지 않습니다"));
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    //
    public MemberTokenResponse createToken(final MemberLoginRequest memberLoginRequest) {
        Member member = validateLoginEmail(memberLoginRequest);
        validatePassword(memberLoginRequest.password(), member.getPassword());

        String role = assignRole(memberLoginRequest.email()).getRole();
        String accessToken = jwtTokenProvider.createToken(memberLoginRequest.email(), role);
        return new MemberTokenResponse(accessToken);
    }

    private Member validateLoginEmail(final MemberLoginRequest memberLoginRequest) {
        return memberRepository.findByEmail(memberLoginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("로그인에 실패했습니다!"));
    }

    //
    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
                .map(member -> new MemberResponse(
                        member.getId(),
                        member.getName(),
                        member.getEmail())
                )
                .toList();
    }

    private Role assignRole(String email) {
        Member member = memberRepository.findByEmail(email).get();
        return member.getRole();
    }

    private void validatePassword(String email, String password) {
        Member member = memberRepository.findByEmail(email).get();
        if (!password.equals(member.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다");
        }
    }
}
