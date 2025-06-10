package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class GymRepositoryTest {

    @Autowired
    private GymRepository gymRepository;

    @Test
    @DisplayName("헬스장을 저장한다.")
    void save() {
        var gym = new Gym("짐박스", new Address("도로명주소", "상세주소"));

        var savedGym = gymRepository.save(gym);

        assertThat(gymRepository.findById(savedGym.getId())).hasValue(gym);
    }
}
