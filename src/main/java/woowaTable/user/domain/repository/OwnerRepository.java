package woowaTable.user.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import woowaTable.user.domain.Email;
import woowaTable.user.domain.Owner;
import woowaTable.user.domain.Password;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByEmailAndPassword(Email email, Password password);

    boolean existsByEmail(Email email);
}
