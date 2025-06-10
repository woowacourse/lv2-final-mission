package finalmission.auth;

import finalmission.exception.AuthNotExistsPhoneNumberException;
import finalmission.member.MemberRepository;
import finalmission.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;


    public String generateToken(final String phoneNumber) {
        Member foundMember = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(AuthNotExistsPhoneNumberException::new);

        return jwtProvider.provideToken(foundMember.getPhoneNumber());
    }
}
