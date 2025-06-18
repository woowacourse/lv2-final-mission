package finalmission.controller.member.repository;

import finalmission.controller.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmailAndPassword(String email, String password);

}
