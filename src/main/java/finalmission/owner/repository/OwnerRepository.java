package finalmission.owner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import finalmission.owner.domain.Owner;
import finalmission.user.domain.User;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByUser(User user);

    default Owner getByUser(User user) {
        return findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("사장님으로 등록되지 않은 사용자입니다."));
    }
}
