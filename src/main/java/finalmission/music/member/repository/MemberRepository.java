package finalmission.music.member.repository;

import finalmission.music.member.domain.Member;
import org.springframework.data.repository.ListCrudRepository;

public interface MemberRepository extends ListCrudRepository<Member, String> {
}
