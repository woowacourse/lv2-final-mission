package finalmission.member.service;

import finalmission.auth.provider.JwtTokenProvider;
import finalmission.exception.ConflictException;
import finalmission.exception.UnauthorizedException;
import finalmission.member.controller.dto.request.LoginRequest;
import finalmission.member.controller.dto.request.SignUpRequest;
import finalmission.member.controller.dto.response.SignUpResponse;
import finalmission.member.entity.Member;
import finalmission.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public SignUpResponse signup(SignUpRequest signUpRequest) {
        validateConflict(signUpRequest.nickname());
        Member member = signUpRequest.toMember();
        Member savedMember = memberRepository.save(member);
        return SignUpResponse.from(savedMember);
    }

    private void validateConflict(String nickname) {
        boolean isExist = memberRepository.findByNickname(nickname).isPresent();
        if (isExist) {
            throw new ConflictException("이미 존재하는 유저입니다");
        }
    }

    public String login(LoginRequest loginRequest) {
        String nickname = loginRequest.nickname();
        String password = loginRequest.password();

        Member findMember = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new UnauthorizedException("사용자를 찾을 수 없습니다"));

        validateLoginInfo(findMember, nickname, password);

        String payload = findMember.getId().toString();
        return jwtTokenProvider.createToken(payload);
    }

    private void validateLoginInfo(Member findMember, String nickname, String password) {
        if (findMember.invalidLoginInfo(nickname, password)) {
            throw new UnauthorizedException("로그인 정보가 올바르지 않습니다");
        }
    }
}
