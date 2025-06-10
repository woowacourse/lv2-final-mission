package finalmission.service;

import finalmission.auth.CookieExtractor;
import finalmission.auth.TokenProvider;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.dto.LoginMemberInfo;
import finalmission.dto.MemberLoginRequest;
import finalmission.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final CookieExtractor cookieExtractor;

    public ResponseCookie loginWithCookie(MemberLoginRequest request) {
        Member loginMember = memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new AuthException("존재하지 않는 이메일 혹은 비밀번호입니다."));
        String token = tokenProvider.createToken(String.valueOf(loginMember.id()));
        return cookieExtractor.createCookieByToken(token);
    }

    public LoginMemberInfo findLoginMemberInfoByToken(String token) {
        long memberId = Long.parseLong(tokenProvider.parsePayload(token));
        Member loginMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new AuthException("존재하지 않는 회원 정보입니다."));
        return new LoginMemberInfo(loginMember);
    }
}

