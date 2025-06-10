package finalmission.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GymTest {

    @Test
    @DisplayName("헬스장을 생성한다.")
    void newGym() {
        var gym = new Gym("짐박스", new Address("군자로 123-1", "지하 1층"));
        assertThat(gym).isNotNull();
    }
}
