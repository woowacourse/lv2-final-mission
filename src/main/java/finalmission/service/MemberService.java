package finalmission.service;

import finalmission.controller.dto.MemberLoginRequest;
import finalmission.controller.dto.MemberResponse;
import finalmission.controller.dto.MemberSignupRequest;
import finalmission.domain.Member;
import finalmission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse signup(MemberSignupRequest request) {
        validateDuplicateEmail(request.email());
        validateDuplicatePhoneNumber(request.phoneNumber());

        Member member = request.toMember();
        memberRepository.save(member);

        log.info("[회원 가입] nickname: {}, email: {}, phoneNumber: {}",
                request.nickname(),
                request.email(),
                request.phoneNumber()
        );
        return new MemberResponse(member);
    }

    public Member validateLoginAndReturnMember(MemberLoginRequest request) {
        Member member = getMemberByEmail(request.email());
        validatePassword(request, member);
        return member;
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            log.warn("[회원 가입 실패] 이메일 중복 - email: {}", email);
            throw new IllegalArgumentException("이미 가입된 이메일이 존재합니다. 다른 이메일을 이용해 주세요.");
        }
    }

    private void validateDuplicatePhoneNumber(String phoneNumber) {
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            log.warn("[회원 가입 실패] 전화번호 중복 - phoneNumber: {}", phoneNumber);
            throw new IllegalArgumentException("이미 가입된 전화번호가 존재합니다. 다른 전화번호를 이용해 주세요.");
        }
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 및 비밀번호가 일치하지 않습니다."));
    }

    private void validatePassword(MemberLoginRequest request, Member member) {
        if (!member.matchesPassword(request.password())) {
            throw new IllegalArgumentException("이메일 및 비밀번호가 일치하지 않습니다.");
        }
    }
}
