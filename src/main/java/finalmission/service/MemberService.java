package finalmission.service;

import finalmission.controller.dto.MemberSignUpRequest;
import finalmission.domain.Member;
import finalmission.domain.vo.LolName;
import finalmission.exception.NotFoundException;
import finalmission.exception.UnauthorizedException;
import finalmission.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final MemberRepository memberRepository;
    private final RiotRestClient riotRestClient;

    @Transactional
    public void create(final MemberSignUpRequest request) {
        if (memberRepository.existsByLolName(request.lolName())) {
            throw new IllegalArgumentException("이미 가입한 유저입니다.");
        }

        if (!riotRestClient.existsLolName(request.lolName())) {
            throw new NotFoundException("존재하지 않는 RIOT 이름 및 태그입니다.");
        }
        final Member member = request.toMember();

        memberRepository.save(member);
    }

    public String createToken(
            final LolName lolName,
            final String password
    ) {
        final Member member = memberRepository.findByLolName(lolName)
                .orElseThrow(() -> new NotFoundException("해당하는 이름의 회원이 존재하지 않습니다."));

        if (!member.isPasswordCorrect(password)) {
            throw new UnauthorizedException("비밀번호가 틀립니다.");
        }

        return Jwts.builder()
                .setSubject(member.getId().toString())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public Member getById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당하는 memberId의 멤버를 찾을 수 없습니다. memberId = " + memberId));
    }
}
