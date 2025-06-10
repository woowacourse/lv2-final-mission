package finalmission.ballparkreservation.reservation;

import finalmission.ballparkreservation.auth.dto.LoginMember;
import finalmission.ballparkreservation.member.Member;
import finalmission.ballparkreservation.member.MemberService;
import finalmission.ballparkreservation.reservation.dto.ReservationCreateRequest;
import finalmission.ballparkreservation.reservation.dto.ReservationCreateResponse;
import finalmission.ballparkreservation.schedule.Schedule;
import finalmission.ballparkreservation.schedule.ScheduleService;
import finalmission.ballparkreservation.schedule.SeatRank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberService memberService;
    private final ScheduleService scheduleService;

    @Transactional
    public ReservationCreateResponse create(final ReservationCreateRequest request, final LoginMember loginMember) {
        Member member = memberService.getById(loginMember.id());
        int seatNumber = request.seatNumber();
        SeatRank seatRank = SeatRank.fromName(request.seatRank());

        Schedule schedule = scheduleService.getByRankAndNumberAndDate(seatRank, seatNumber, request.date());

        Reservation reservation = new Reservation(member, schedule);
        validateReservationIsAbleToCreate(reservation);

//        Long amount = reservation.getAmount(공휴일인지);
        return new ReservationCreateResponse(0L);
    }

    private void validateReservationIsAbleToCreate(final Reservation reservation) {
        if (reservationRepository.existsBySchedule(reservation.getSchedule())) {
            throw new IllegalArgumentException("이미 해당 날짜 좌석에 대한 예약이 존재합니다.");
        }
    }
}
