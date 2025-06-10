package finalmission.infrastructure.member;

import finalmission.domain.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface JpaMemberRepository extends CrudRepository<Member, Long> {

    Member findByNameAndPhoneNumber(final String name, final String phoneNumber);
}
