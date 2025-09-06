package finalmission.member.service;

import finalmission.exception.ConflictException;
import finalmission.exception.NotFoundException;
import finalmission.exception.UnauthorizedException;
import finalmission.member.dto.LoginRequest;
import finalmission.member.dto.SignupRequest;
import finalmission.member.dto.SignupResponse;
import finalmission.member.jwt.TokenCreateRequest;
import finalmission.member.model.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.member.service.api.NameGenerator;
import finalmission.util.PasswordTransformer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordTransformer passwordTransformer;
    private final NameGenerator nameGenerator;

    public SignupResponse signup(SignupRequest request) {
        validateDuplicateEmail(request.email());
        Member member = memberRepository.save(buildMember(request));
        return SignupResponse.success(member);
    }

    public TokenCreateRequest login(LoginRequest request) {
        Member member = findMemberByEmail(request.email());
        if (member.isPasswordMatch(passwordTransformer.encode(request.password()))) {
            return new TokenCreateRequest(member.getId());
        }
        throw UnauthorizedException.passwordMismatch();
    }

    private Member findMemberByEmail(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isEmpty()) {
            throw NotFoundException.memberNotFound();
        }
        return memberOptional.get();
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw ConflictException.memberAlreadyExist();
        }
    }

    private Member buildMember(SignupRequest request) {
        return new Member(nameGenerator.generate(), request.email(), passwordTransformer.encode(request.password()));
    }
}
