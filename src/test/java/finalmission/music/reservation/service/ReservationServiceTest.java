package finalmission.music.reservation.service;

import finalmission.music.album.domain.Album;
import finalmission.music.album.repository.AlbumRepository;
import finalmission.music.common.BaseServiceTest;
import finalmission.music.lottery.domain.Lottery;
import finalmission.music.lottery.repository.LotteryRepository;
import finalmission.music.member.domain.Member;
import finalmission.music.member.domain.Role;
import finalmission.music.member.repository.MemberRepository;
import finalmission.music.reservation.controller.dto.ReservationResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReservationServiceTest extends BaseServiceTest {

    @Autowired
    private ReservationService sut;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private LotteryRepository lotteryRepository;

    @Test
    @DisplayName("지나간 추첨에 대해서 예약할 수 없다.")
    void dont_reserve_to_expired_lottery() {
        Member member = memberRepository.save(new Member("test-member", Role.USER));
        Album album = albumRepository.save(
            new Album("test", "test", 0, LocalDate.now(), "test-id")
        );
        Lottery lottery = lotteryRepository.save(new Lottery(album, LocalDateTime.now().minusDays(1)));

        Assertions.assertThatThrownBy(() -> sut.reserve(lottery.getId(), "test-address", member.getName()))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("올바른 추첨에 대해서 예약할 수 있다.")
    void can_reserve_to_lottery() {
        Member member = memberRepository.save(new Member("test-member", Role.USER));
        Album album = albumRepository.save(
            new Album("test", "test", 0, LocalDate.now(), "test-id")
        );
        Lottery lottery = lotteryRepository.save(new Lottery(album, LocalDateTime.now().plusDays(1)));

        ReservationResponse response = sut.reserve(lottery.getId(), "test-address", member.getName());

        Assertions.assertThat(response.lotteryId()).isEqualTo(lottery.getId());
    }
}
