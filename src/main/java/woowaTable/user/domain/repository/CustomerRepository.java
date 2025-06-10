package woowaTable.user.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import woowaTable.user.domain.Customer;
import woowaTable.user.domain.Email;
import woowaTable.user.domain.Password;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailAndPassword(final Email email, final Password password);

    boolean existsByEmail(Email email);
}
