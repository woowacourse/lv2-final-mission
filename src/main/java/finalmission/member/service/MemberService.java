package finalmission.member.service;

import finalmission.member.domain.Member;
import finalmission.member.dto.MemberRegistrationRequest;
import finalmission.member.dto.MemberRegistrationResponse;
import finalmission.member.infrastructure.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JpaMemberRepository jpaMemberRepository;

    public MemberRegistrationResponse registerMember(final MemberRegistrationRequest registrationDto) {
        Member memberWithoutId = Member.createMemberWithoutId(registrationDto.name(), registrationDto.email(), registrationDto.password(), registrationDto.phoneNumber());
        Member savedMember = jpaMemberRepository.save(memberWithoutId);
        return MemberRegistrationResponse.from(savedMember);
    }
}
