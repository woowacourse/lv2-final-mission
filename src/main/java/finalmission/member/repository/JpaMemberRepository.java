package finalmission.member.repository;

import finalmission.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmailAndPassword(String email, String password);

}
