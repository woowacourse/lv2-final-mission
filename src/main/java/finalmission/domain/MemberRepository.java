package finalmission.domain;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(long id);

    Optional<Member> findByEmailAndPassword(String email, String password);
}

