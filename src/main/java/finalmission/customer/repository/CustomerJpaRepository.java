package finalmission.customer.repository;

import finalmission.customer.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByName(String name);

    boolean existsByEmailAndPassword(String email, String password);

    Optional<Customer> findByEmail(String payload);
}
