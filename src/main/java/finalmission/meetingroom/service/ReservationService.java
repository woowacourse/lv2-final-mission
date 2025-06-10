package finalmission.meetingroom.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finalmission.meetingroom.common.exception.EntityNotFoundException;
import finalmission.meetingroom.domain.MeetingRoom;
import finalmission.meetingroom.domain.Member;
import finalmission.meetingroom.domain.Reservation;
import finalmission.meetingroom.repository.MeetingRoomRepository;
import finalmission.meetingroom.repository.MemberRepository;
import finalmission.meetingroom.repository.ReservationRepository;
import finalmission.meetingroom.service.request.LoginMember;
import finalmission.meetingroom.service.request.ReservationCreateRequest;
import finalmission.meetingroom.service.request.ReservationTimeChangeRequest;
import finalmission.meetingroom.service.response.ReservationResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationService {

    private static final LocalTime START_TIME = LocalTime.of(10, 0);
    private static final LocalTime END_TIME = LocalTime.of(22, 0);

    private final ReservationRepository reservationRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReservationResponse reserveMeetingRoom(
            final ReservationCreateRequest request,
            final LoginMember loginMember
    ) {
        if (isPastTime(request)) {
            throw new IllegalArgumentException("");
        }
        if (isAvailableTime(request)) {
            throw new IllegalArgumentException("");
        }

        MeetingRoom meetingRoom = getMeetingRoom(request.meetingRoomName());
        if (isAlreadyReserved(meetingRoom, request)) {
            throw new IllegalArgumentException("");
        }
        Member member = getMember(loginMember);

        Reservation reservation = new Reservation(
                meetingRoom, member, request.reservationDate(), request.startAt(), request.endAt()
        );
        reservationRepository.save(reservation);

        return ReservationResponse.from(reservation);
    }

    private boolean isPastTime(final ReservationCreateRequest request) {
        LocalDateTime reservationStartDateTime = LocalDateTime.of(request.reservationDate(), request.startAt());
        return LocalDateTime.now().isAfter(reservationStartDateTime);
    }

    private boolean isAvailableTime(final ReservationCreateRequest request) {
        LocalTime reservationStartTime = request.startAt();
        return START_TIME.isAfter(reservationStartTime) || END_TIME.isBefore(reservationStartTime);
    }

    private boolean isAlreadyReserved(final MeetingRoom meetingRoom, final ReservationCreateRequest request) {
        LocalTime startTime = request.startAt();
        LocalTime endTime = request.endAt();
        return !reservationRepository.existsByMeetingRoomAndReservationDateAndStartAtBetween(
                meetingRoom, request.reservationDate(), startTime, endTime) &&
               reservationRepository.existsByMeetingRoomAndReservationDateAndEndAtBetween(
                       meetingRoom, request.reservationDate(), startTime, endTime);
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<ReservationResponse> getMyReservations(final LoginMember loginMember) {
        Member member = getMember(loginMember);
        return reservationRepository.findByMember(member)
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse changeReservationTime(
            final Long reservationId,
            final ReservationTimeChangeRequest request,
            final LoginMember loginMember
    ) {
        Member member = getMember(loginMember);
        Reservation reservation = reservationRepository.findByIdAndMember(reservationId, member)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회의실 예약 입니다."));

        reservation.changeReservationTime(request.newStartAt(), request.newEndAt());

        return ReservationResponse.from(reservation);
    }

    private MeetingRoom getMeetingRoom(final String meetingRoomName) {
        return meetingRoomRepository.findByRoomName(meetingRoomName)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회의실 입니다."));
    }

    private Member getMember(final LoginMember loginMember) {
        return memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
    }
}
