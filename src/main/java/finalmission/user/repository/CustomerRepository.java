package finalmission.user.repository;

import finalmission.user.domain.Customer;
import finalmission.user.domain.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(Email email);
}
