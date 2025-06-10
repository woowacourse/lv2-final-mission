package finalmission.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.TestFixtures;
import finalmission.domain.BookingRepository;
import finalmission.domain.Gym;
import finalmission.domain.GymRepository;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BookingServiceTest {

    private final MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private final GymRepository gymRepository = Mockito.mock(GymRepository.class);
    private final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private final BookingService bookingService = new BookingService(memberRepository, gymRepository, bookingRepository);

    @Test
    @DisplayName("예약을 한다.")
    void book() {
        var member = anyMemberWithMocking();
        var gym = anyGymWithMocking();
        var date = LocalDate.of(2025, 6, 17);

        bookingService.book(member.getId(), gym.getId(), date);

        Mockito.verify(bookingRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("사용자나 헬스장이 존재하지 않는 경우 예약할 수 없다.")
    void cannotBookNotFoundUserOrGym() {
        var member = anyMemberWithMocking();
        var gym = TestFixtures.anyGym();
        Mockito.doThrow(NoSuchElementException.class).when(gymRepository).getById(gym.getId());

        var date = LocalDate.of(2025, 6, 17);

        assertThatThrownBy(() -> bookingService.book(member.getId(), gym.getId(), date))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("과거 날짜로 예약할 수 없다.")
    void cannotBookPast() {
        var member = anyMemberWithMocking();
        var gym = anyGymWithMocking();
        var date = LocalDate.of(2020, 1, 1);

        assertThatThrownBy(() -> bookingService.book(member.getId(), gym.getId(), date))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private Member anyMemberWithMocking() {
        var member = TestFixtures.anyMember();
        Mockito.doReturn(member).when(memberRepository).getById(member.getId());
        return member;
    }

    private Gym anyGymWithMocking() {
        var gym = TestFixtures.anyGym();
        Mockito.doReturn(gym).when(gymRepository).getById(gym.getId());
        return gym;
    }
}
