package finalmission.member.service;

import finalmission.member.controller.dto.request.MemberRequest;
import finalmission.member.domain.Member;
import finalmission.member.repository.JpaMemberRepository;
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
