package finalmission.unit.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import finalmission.domain.Guest;
import finalmission.exception.InvalidGuestSizeException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GuestTest {
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 15})
    void 잘못된_게스트_숫자(int guest) {
        assertThatCode(() -> new Guest(guest))
                .isInstanceOf(InvalidGuestSizeException.class)
                .hasMessage("인원 수는 1명 이상, 14명 이하여야 합니다.");
    }
}
