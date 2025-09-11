package finalmission.unit.toilet.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.toilet.domain.Toilet;
import finalmission.toilet.infrastructure.ToiletRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ToiletRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ToiletRepository toiletRepository;

    @Test
    void 화장실을_저장한다() {
        // given
        Toilet toilet = new Toilet("position");
        // when
        Toilet savedToilet = toiletRepository.save(toilet);
        // then
        Toilet foundToilet = entityManager.find(Toilet.class, savedToilet.getId());
        assertThat(foundToilet).isNotNull();
        assertThat(foundToilet.getPosition()).isEqualTo("position");
    }

    @Test
    void 모든_화장실을_조회한다() {
        // given
        Toilet toilet1 = new Toilet("position1");
        entityManager.persist(toilet1);
        Toilet toilet2 = new Toilet("position2");
        entityManager.persist(toilet2);
        // when
        List<Toilet> toilets = toiletRepository.findAll();
        // then
        assertThat(toilets).hasSize(2);
        assertThat(toilets).extracting("position")
                .containsExactlyInAnyOrder("position1", "position2");
    }

    @Test
    void 화장실이_없으면_빈_리스트를_반환한다() {
        // when
        List<Toilet> toilets = toiletRepository.findAll();
        // then
        assertThat(toilets).isEmpty();
    }

    @Test
    void ID로_화장실을_조회한다() {
        // given
        Toilet toilet = new Toilet("position");
        entityManager.persist(toilet);
        // when
        Optional<Toilet> foundToilet = toiletRepository.findById(toilet.getId());
        // then
        assertThat(foundToilet).isPresent();
        assertThat(foundToilet.get().getPosition()).isEqualTo("position");
    }

    @Test
    void ID가_존재하지_않으면_빈_Optional을_반환한다() {
        // when
        Optional<Toilet> foundToilet = toiletRepository.findById(999L);
        // then
        assertThat(foundToilet).isEmpty();
    }
}