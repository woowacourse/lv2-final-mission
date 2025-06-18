package finalmission.member.service;

import finalmission.member.domain.Member;
import finalmission.member.dto.MemberCreateRequest;
import finalmission.member.dto.MemberCreateResponse;
import finalmission.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final RandomNameService randomNameService;
    private final MemberRepository memberRepository;

    public MemberService(RandomNameService randomNameService, MemberRepository memberRepository) {
        this.randomNameService = randomNameService;
        this.memberRepository = memberRepository;
    }

    public MemberCreateResponse create(MemberCreateRequest request) {
        String name = randomNameService.createRandomName(1)[0];
        while (memberRepository.existsByName(name)) {
            name = randomNameService.createRandomName(1)[0];
        }
        Member member = new Member(null, name, request.email(), request.password());
        Member savedMember = memberRepository.save(member);
        return MemberCreateResponse.from(savedMember);
    }
}
