package finalmission.reservation.service;

import finalmission.external.holiday.HolidayRestClient;
import finalmission.global.AuthenticationPrincipal;
import finalmission.meetingroom.domain.MeetingRoom;
import finalmission.meetingroom.repository.MeetingRoomRepository;
import finalmission.member.domian.Member;
import finalmission.member.dto.LoginMember;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.reservationtime.domain.ReservationTime;
import finalmission.reservationtime.repository.ReservationTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final MemberRepository memberRepository;
    private final ReservationTimeRepository timeRepository;
    private final HolidayRestClient holidayRestClient;

    public ReservationResponse addReservation(
            ReservationRequest request,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        if (holidayRestClient.checkHoliday(request.date())) {
            throw new IllegalArgumentException("공휴일엔 예약할 수 없습니다.");
        }

        MeetingRoom meetingRoom = getMeetingRoom(request.roomId());

        Member member = getMember(loginMember);

        ReservationTime reservationTime = timeRepository.findById(request.timeId())
                .orElseThrow(() -> new NoSuchElementException("해당 예약 시간은 존재하지 않습니다."));

        Reservation reservation = new Reservation(request.date(), reservationTime, member, meetingRoom);
        Reservation savedReservation = reservationRepository.save(reservation);
        return new ReservationResponse(savedReservation.getId(), savedReservation.getDate(), savedReservation.getTime().getStartAt(), savedReservation.getMeetingRoom().getRoomName());
    }

    public List<ReservationResponse> findAllByMember(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        Member member = getMember(loginMember);
        List<Reservation> reservations = reservationRepository.findByMember((member));
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void deleteReservationById(Long reservationId, LoginMember loginMember) {
        if (!reservationRepository.existsReservationByIdAndMemberId(reservationId, loginMember.id())) {
            throw new NoSuchElementException("해당 예약은 존재하지 않습니다.");
        }
        reservationRepository.deleteById(reservationId);
    }

    private MeetingRoom getMeetingRoom(Long roomId) {
        return meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("해당 회의실은 존재하지 않습니다."));
    }

    private Member getMember(LoginMember loginMember) {
        return memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new NoSuchElementException("해당 멤버는 존재하지 않는 회원입니다."));
    }
}
