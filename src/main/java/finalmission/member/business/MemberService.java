package finalmission.member.business;

import finalmission.general.auth.util.JwtProvider;
import finalmission.member.database.MemberRepository;
import finalmission.member.model.Member;
import finalmission.member.presentation.dto.request.MemberCreateWebRequest;
import finalmission.member.presentation.dto.request.MemberLoginWebRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    public Member createUser(MemberCreateWebRequest memberCreateWebRequest) {
        String username = memberCreateWebRequest.username();
        validateDuplicatedUsername(username);
        return memberRepository.save(new Member(username, memberCreateWebRequest.password(), memberCreateWebRequest.name()));
    }

    private void validateDuplicatedUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    public String login(MemberLoginWebRequest memberLoginWebRequest) {
        Member member = memberRepository.findByUsernameAndPassword(memberLoginWebRequest.username(), memberLoginWebRequest.password())
                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호를 잘못 입력하셨습니다."));
        return jwtProvider.generateToken(member.getUsername());
    }
}
