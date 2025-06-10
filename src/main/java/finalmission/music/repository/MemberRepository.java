package finalmission.music.repository;

import finalmission.music.domain.Member;
import org.springframework.data.repository.ListCrudRepository;

public interface MemberRepository extends ListCrudRepository<Member, Long> {
}
