package finalmission.member.repository;

import finalmission.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    Member findByEmailAndPassword(@NotBlank String email, @NotBlank String password);
}
