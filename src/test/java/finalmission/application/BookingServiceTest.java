package finalmission.application;

import static finalmission.TestFixtures.*;
import static finalmission.TestFixtures.anyBooking;
import static finalmission.TestFixtures.anyFutureLocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.TestFixtures;
import finalmission.domain.AuthenticationException;
import finalmission.domain.booking.Booking;
import finalmission.domain.booking.BookingDate;
import finalmission.domain.booking.BookingRepository;
import finalmission.domain.gym.Gym;
import finalmission.domain.gym.GymRepository;
import finalmission.domain.HolidayChecker;
import finalmission.domain.member.Member;
import finalmission.domain.member.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BookingServiceTest {

    private final MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private final GymRepository gymRepository = Mockito.mock(GymRepository.class);
    private final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private final HolidayChecker holidayChecker = Mockito.mock(HolidayChecker.class);
    private final BookingService bookingService = new BookingService(memberRepository, gymRepository, bookingRepository, holidayChecker);

    private final Member member = anyMemberWithMocking();
    private final Gym gym = anyGymWithMocking();
    private final LocalDate date = anyFutureLocalDate();

    @Test
    @DisplayName("예약을 한다.")
    void book() {
        bookingService.book(member.getId(), gym.getId(), date);

        Mockito.verify(bookingRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("사용자나 헬스장이 존재하지 않는 경우 예약할 수 없다.")
    void cannotBookNotFoundUserOrGym() {
        Mockito.doThrow(NoSuchElementException.class).when(gymRepository).getById(gym.getId());

        var date = LocalDate.of(2025, 6, 17);

        assertThatThrownBy(() -> bookingService.book(member.getId(), gym.getId(), date))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("과거 날짜로 예약할 수 없다.")
    void cannotBookPast() {
        var date = LocalDate.of(2020, 2, 2);

        assertThatThrownBy(() -> bookingService.book(member.getId(), gym.getId(), date))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("공휴일에는 예약할 수 없다.")
    void cannotBookHoliday() {
        var christmas = LocalDate.of(2020, 12, 25);

        Mockito.doReturn(true).when(holidayChecker).isHoliday(christmas);

        assertThatThrownBy(() -> bookingService.book(member.getId(), gym.getId(), christmas))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("자신의 예약들을 받아 볼 수 있다.")
    void getMyBookings() {
        Mockito
            .doReturn(List.of(anyBooking(), anyBooking(), anyBooking()))
            .when(bookingRepository).findAllByMember(member);

        var myBookings = bookingService.getMyBookings(member.getId());

        assertThat(myBookings).hasSize(3);
    }

    @Test
    @DisplayName("예약 날짜를 수정한다.")
    void modifyDate() {
        // given
        var booking = new Booking(member, gym, BookingDate.of(anyFutureLocalDate()));
        Mockito.doReturn(booking).when(bookingRepository).getById(booking.getId());
        Mockito.doReturn(booking.getMember()).when(memberRepository).getById(booking.getMember().getId());

        // when
        var dateToModify = anyFutureLocalDate();
        var modifiedBooking = bookingService.modifyDate(booking.getId(), member.getId(), dateToModify);

        // then
        var expectedBookingDate = BookingDate.of(dateToModify);
        assertThat(modifiedBooking.getDate()).isEqualTo(expectedBookingDate);
    }

    @Test
    @DisplayName("예약 날짜를 수정할 때 본인의 예약이 아니면 수정할 수 없다.")
    void modifyDateCanOnlyMine() {
        // given
        var booking = new Booking(member, gym, BookingDate.of(anyFutureLocalDate()));
        Mockito.doReturn(booking).when(bookingRepository).getById(booking.getId());
        Mockito.doReturn(booking.getMember()).when(memberRepository).getById(booking.getMember().getId());

        // when & then
        assertThatThrownBy(() -> bookingService.modifyDate(booking.getId(), anyMember().getId(), anyFutureLocalDate()))
            .isInstanceOf(AuthenticationException.class);
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void cancelBooking() {
        // given
        var booking = new Booking(member, gym, BookingDate.of(anyFutureLocalDate()));
        Mockito.doReturn(booking).when(bookingRepository).getById(booking.getId());
        Mockito.doReturn(booking.getMember()).when(memberRepository).getById(booking.getMember().getId());

        // when
        bookingService.cancel(booking.getId(), member.getId());

        // then
        var memberBookings = bookingService.getMyBookings(member.getId());
        assertThat(memberBookings).doesNotContain(booking);
    }

    @Test
    @DisplayName("예약을 취소할 때 본인의 예약이 아니면 취소할 수 없다.")
    void cancelBookingCanOnlyMine() {
        // given
        var booking = new Booking(member, gym, BookingDate.of(anyFutureLocalDate()));
        Mockito.doReturn(booking).when(bookingRepository).getById(booking.getId());
        Mockito.doReturn(booking.getMember()).when(memberRepository).getById(booking.getMember().getId());

        // when & then
        assertThatThrownBy(() -> bookingService.cancel(booking.getId(), anyMember().getId()))
            .isInstanceOf(AuthenticationException.class);
    }

    private Member anyMemberWithMocking() {
        var member = anyMember();
        Mockito.doReturn(member).when(memberRepository).getById(member.getId());
        return member;
    }

    private Gym anyGymWithMocking() {
        var gym = anyGym();
        Mockito.doReturn(gym).when(gymRepository).getById(gym.getId());
        return gym;
    }
}
