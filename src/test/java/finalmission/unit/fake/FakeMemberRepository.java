package finalmission.unit.fake;

import finalmission.domain.Member;
import finalmission.infrastructure.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeMemberRepository implements MemberRepository {

    private final List<Member> members = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public Member save(Member member) {
        Member newMember = new Member(index.getAndIncrement(), member.getEmail(), member.getName(),
                member.getPassword());
        members.add(newMember);
        return newMember;
    }
}
