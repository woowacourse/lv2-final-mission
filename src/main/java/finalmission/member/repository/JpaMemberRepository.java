package finalmission.member.repository;

import finalmission.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member, Long> {

}
