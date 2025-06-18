package finalmission.member.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("이름으로 Name 객체를 생성한다")
    @Test
    void create() {
        // given
        String nameValue = "testName";

        // when & then
        assertThatCode(() -> new Name(nameValue))
                .doesNotThrowAnyException();
    }
}
