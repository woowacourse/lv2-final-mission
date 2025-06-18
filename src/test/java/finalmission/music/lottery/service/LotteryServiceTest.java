package finalmission.music.lottery.service;

import finalmission.music.album.domain.Album;
import finalmission.music.album.repository.AlbumRepository;
import finalmission.music.common.BaseServiceTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class LotteryServiceTest extends BaseServiceTest {

    @Autowired
    private LotteryService sut;

    @Autowired
    private AlbumRepository albumRepository;

    @ParameterizedTest
    @MethodSource("expiredDateTime")
    @DisplayName("마감 기한이 지난 추첨은 생성할 수 없다.")
    void dont_create_expired_reservation(final LocalDateTime expiredDateTime) {
        Album album = albumRepository.save(new Album("test", "test-artist", 0, LocalDate.now(), "test-id"));

        Assertions.assertThatThrownBy(() -> sut.createLottery(album.getId(), expiredDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> expiredDateTime() {
        return Stream.of(
            Arguments.arguments(LocalDateTime.now().minusDays(1)),
            Arguments.arguments(LocalDateTime.now().minusDays(3)),
            Arguments.arguments(LocalDateTime.now().minusHours(1)),
            Arguments.arguments(LocalDateTime.now().minusMinutes(1))
        );
    }

    @Test
    @DisplayName("존재하지 않는 앨범에 대해서 생성할 수 없다.")
    void dont_create_with_non_exists_album_id() {
        Assertions.assertThatThrownBy(() -> sut.createLottery(1L, LocalDateTime.now().plusDays(1)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
