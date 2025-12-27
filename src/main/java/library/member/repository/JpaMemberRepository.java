package library.member.repository;

import java.util.List;
import library.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member,Long> {
    List<Member> findByEmail(String email);
}
