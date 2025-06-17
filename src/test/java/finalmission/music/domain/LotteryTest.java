package finalmission.music.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LotteryTest {

    @ParameterizedTest
    @MethodSource("expirationAndAnswer")
    @DisplayName("지나간 추첨인지 스스로 판별할 수 있다.")
    void can_determine_is_expired(final LocalDateTime expiration, final boolean answer) {
        Album album = new Album("test", "test", 0, LocalDate.now(), "test-id");
        Lottery lottery = new Lottery(album, expiration);

        boolean expired = lottery.isExpired();

        Assertions.assertThat(expired).isEqualTo(answer);
    }

    static Stream<Arguments> expirationAndAnswer() {
        return Stream.of(
            Arguments.arguments(LocalDateTime.now().minusDays(1), true),
            Arguments.arguments(LocalDateTime.now().minusDays(4), true),
            Arguments.arguments(LocalDateTime.now().minusDays(6), true),
            Arguments.arguments(LocalDateTime.now().plusDays(1), false),
            Arguments.arguments(LocalDateTime.now().plusDays(4), false),
            Arguments.arguments(LocalDateTime.now().plusDays(6), false),
            Arguments.arguments(LocalDateTime.now().plusHours(1), false),
            Arguments.arguments(LocalDateTime.now().minusHours(1), true)
        );
    }
}
