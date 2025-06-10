package finalmission.mungPlan.infra;

import finalmission.mungPlan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
