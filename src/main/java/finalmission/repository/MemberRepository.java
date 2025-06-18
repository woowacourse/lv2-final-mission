package finalmission.repository;

import finalmission.domain.Member;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findMemberById(long id);
    Member save(Member member);
    Optional<Member> findByEmailAndPassword(String email, String password);
}
