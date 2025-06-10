package finalmission.member.service;

import finalmission.member.domain.Member;
import finalmission.member.dto.MemberRegistrationRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository jpaMemberRepository;

    public MemberResponse registerMember(final MemberRegistrationRequest registrationDto) {
        Member memberWithoutId = Member.createMemberWithoutId(registrationDto.name(), registrationDto.email(), registrationDto.password(), registrationDto.phoneNumber());
        Member savedMember = jpaMemberRepository.save(memberWithoutId);
        return MemberResponse.from(savedMember);
    }
}
