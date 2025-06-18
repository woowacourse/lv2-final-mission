package finalmission.member.repository;

import finalmission.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

}
