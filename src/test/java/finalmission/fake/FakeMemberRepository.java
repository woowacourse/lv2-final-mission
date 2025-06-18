package finalmission.fake;

import finalmission.domain.Member;
import finalmission.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.test.util.ReflectionTestUtils;

public class FakeMemberRepository implements MemberRepository {

    private long index = 0L;
    private List<Member> members = new ArrayList<>();

    public Member save(Member member) {
        ReflectionTestUtils.setField(member, "id", ++index);
        members.add(member);
        return member;
    }

    @Override
    public Optional<Member> findByEmailAndPassword(String email, String password) {
        return members.stream()
                .filter(member -> member.getEmail().equals(email))
                .filter(member -> member.getPassword().equals(password))
                .findAny();
    }

    @Override
    public Optional<Member> findMemberById(long id) {
        return members.stream()
                .filter(member -> member.getId() == id)
                .findAny();
    }
}
