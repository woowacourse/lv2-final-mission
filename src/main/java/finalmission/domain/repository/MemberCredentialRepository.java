package finalmission.domain.repository;

import finalmission.domain.MemberCredential;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCredentialRepository extends JpaRepository<MemberCredential, Long> {

    Optional<MemberCredential> findByEmail(String email);

    boolean existsByEmail(String email);
}
