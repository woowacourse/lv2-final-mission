package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.MemberRole;
import finalmission.dto.LoginRequest;
import finalmission.dto.SignUpRequest;
import finalmission.dto.SignUpResponse;
import finalmission.exception.BadRequestException;
import finalmission.exception.ErrorCode;
import finalmission.exception.NotFoundException;
import finalmission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMemberById(final Long memberId) {
        return  memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member getMemberByLoginRequest(final LoginRequest loginRequest) {
        final Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        member.validatePassword(loginRequest.password());
        return member;
    }

    @Transactional
    public SignUpResponse signUp(final SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.email())) {
            throw new BadRequestException(ErrorCode.DUPLICATE_EMAIL);
        }
        final Member member = new Member(signUpRequest.email(), signUpRequest.password(), MemberRole.USER);
        return SignUpResponse.from(memberRepository.save(member));
    }
}
