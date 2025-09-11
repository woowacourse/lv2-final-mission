package finalmission.unit.fake;

import finalmission.member.domain.Member;
import finalmission.member.infrastructure.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeMemberRepository implements MemberRepository {

    private final List<Member> members = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public Member save(Member member) {
        Member newMember = new Member(index.getAndIncrement(), member.getEmail(), member.getNickname(),
                member.getPassword());
        members.add(newMember);
        return newMember;
    }

    @Override
    public boolean existsByEmail(String email) {
        return members.stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return members.stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return members.stream()
                .filter(member -> member.getId().equals(memberId))
                .findFirst();
    }
}
