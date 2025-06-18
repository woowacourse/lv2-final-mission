package finalmission.controller.member.service;

import finalmission.controller.member.controller.dto.request.MemberRequest;
import finalmission.controller.member.domain.Member;
import finalmission.controller.member.repository.JpaMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final JpaMemberRepository memberRepository;

    public MemberService(final JpaMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member create(final MemberRequest request) {
        Member member = Member.beforeSave(request.email(),  request.password(),request.name());
        return memberRepository.save(member);
    }
}
