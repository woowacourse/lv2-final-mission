package finalmission.ballparkreservation.reservation;

import finalmission.ballparkreservation.auth.dto.LoginMember;
import finalmission.ballparkreservation.member.Member;
import finalmission.ballparkreservation.member.MemberService;
import finalmission.ballparkreservation.reservation.dto.ReservationCreateRequest;
import finalmission.ballparkreservation.schedule.Schedule;
import finalmission.ballparkreservation.schedule.ScheduleService;
import finalmission.ballparkreservation.schedule.SeatRank;
import finalmission.ballparkreservation.util.TestFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private ScheduleService scheduleService;

    @Test
    @DisplayName("예약하려는 날짜, 좌석에 대한 예약이 이미 존재하는 경우 예외를 발생시킨다")
    void create1() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest("TABLE", 10, LocalDate.now());
        LoginMember loginMember = new LoginMember(1L);
        Member member = TestFactory.memberWithId(1L, new Member("may@gmail.com", "1234", "메이", 24));
        given(memberService.getById(1L))
                .willReturn(member);
        given(scheduleService.getByRankAndNumberAndDate(SeatRank.TABLE, 10, request.date()))
                .willReturn(TestFactory.scheduleWithId(1L, new Schedule(10, SeatRank.TABLE, LocalDate.now())));
        given(reservationRepository.existsBySchedule(any()))
                .willReturn(true);

        // when & then
        Assertions.assertThatThrownBy(() -> reservationService.create(request, loginMember))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
