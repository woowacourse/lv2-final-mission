package finalmission.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import finalmission.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    default User getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자 정보입니다."));
    }

    Optional<User> findByEmail(String email);

    default User getByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다."));
    }
}
