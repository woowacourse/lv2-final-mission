package finalmission.omakase.repository;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.omakase.entity.Omakase;
import finalmission.omakase.entity.Rating;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OmakaseJpaRepositoryTest {

    @Autowired
    OmakaseJpaRepository omakaseJpaRepository;
    @Autowired
    EntityManager em;

    private Omakase omakase;

    @BeforeEach
    void setUp() {
        omakase = new Omakase("스시준", Rating.HIGH_END);
    }

    @Test
    @DisplayName("오마카세 업장을 이름으로 조회한다")
    void findOmakaseByName() {
        //given
        omakaseJpaRepository.save(omakase);
        em.flush();

        //when
        Optional<Omakase> response = omakaseJpaRepository.findByStoreName("스시준");

        //then
        assertThat(response.get().getStoreName()).isEqualTo("스시준");
    }

}
