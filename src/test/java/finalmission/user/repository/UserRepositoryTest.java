package finalmission.user.repository;

import finalmission.user.User;
import finalmission.user.fixture.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    private User user;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        user = UserFixture.createDefault();
    }

    @Nested
    @DisplayName("이메일과 패스워드로 유저 존재 여부 확인")
    class existsByEmailAndPassword {


        @DisplayName("유효한 이메일과 유효한 패스워드로 유저 존재 여부를 확인한다면 true 반환")
        @Test
        void existsByEmailAndPassword_true_byValidEmailAndValidPassword() {
            // given
            User savedUser = userRepository.save(user);

            // when
            boolean actual = userRepository.existsByEmailAndPassword(savedUser.getEmail(), savedUser.getPassword());

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @DisplayName("유요하지 않은 이메일로 유저 존재 여부를 확인한다면 false 반환")
        @Test
        void existsByEmailAndPassword_false_byInvalidEmailAndValidPassword() {
            // given
            User savedUser = userRepository.save(user);

            // when
            boolean actual = userRepository.existsByEmailAndPassword(savedUser.getEmail() + "random", savedUser.getPassword());

            // then
            Assertions.assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("id로 유저 존재 여부 확인")
    class existsById {

        @DisplayName("저장되어 있는 id로 유저 존재 여부를 확인한다면 true를 반환")
        @Test
        void existsById_true_ExistingId() {
            // given
            User savedUser = userRepository.save(user);

            // when
            boolean actual = userRepository.existsById(savedUser.getId());

            // then
            Assertions.assertThat(actual).isTrue();
        }
    }
}
