package finalmission.member.service;

import finalmission.common.exception.InvalidRequestException;
import finalmission.member.domain.Member;
import finalmission.member.dto.MemberRegistrationRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse registerMember(final MemberRegistrationRequest registrationDto) {
        Member memberWithoutId = Member.createMemberWithoutId(registrationDto.name(), registrationDto.email(), registrationDto.password(), registrationDto.phoneNumber());
        Member savedMember = memberRepository.save(memberWithoutId);
        return MemberResponse.from(savedMember);
    }

    public MemberResponse findMemberById(final long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new InvalidRequestException("유저를 찾을 수 없습니다. id : " + id));
        return MemberResponse.from(findMember);
    }
}
