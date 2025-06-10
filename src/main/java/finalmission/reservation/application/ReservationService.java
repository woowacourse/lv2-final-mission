package finalmission.reservation.application;

import finalmission.meetingRoom.domain.MeetingRoom;
import finalmission.meetingRoom.domain.repository.MeetingRoomRepository;
import finalmission.member.domain.Member;
import finalmission.member.domain.repository.MemberRepository;
import finalmission.reservation.application.dto.ReservationRequest;
import finalmission.reservation.application.dto.ReservationResponse;
import finalmission.reservation.application.dto.ReservationUpdateRequest;
import finalmission.reservation.domain.EndAt;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationDate;
import finalmission.reservation.domain.StartAt;
import finalmission.reservation.domain.repository.ReservationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    @Transactional
    public ReservationResponse create(ReservationRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        MeetingRoom meetingRoom = meetingRoomRepository.findById(request.roomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회의실입니다."));
        ReservationDate date = new ReservationDate(request.date());
        StartAt startAt = new StartAt(request.startAt());
        EndAt endAt = new EndAt(request.endAt());

        Reservation reservation = new Reservation(member, meetingRoom, date, startAt, endAt);
        reservationRepository.save(reservation);
        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> getMyReservation(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        List<Reservation> reservations = reservationRepository.findByMember(member);
        return ReservationResponse.from(reservations);
    }

    public List<ReservationResponse> getRoomReservation(Long roomId) {
        List<Reservation> reservations = reservationRepository.findFiltered(roomId);
        return ReservationResponse.from(reservations);
    }

    @Transactional
    public void delete(Long memberId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        validateIsOwner(reservation, memberId);
    }

    private void validateIsOwner(Reservation reservation, Long memberId) {
        if (!reservation.getId().equals(memberId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
    }

    @Transactional
    public ReservationResponse update(Long reservationId, ReservationUpdateRequest request, Long memberId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        validateIsOwner(reservation, memberId);

        if (request.date() != null) {
            ReservationDate date = new ReservationDate(request.date());
            reservation.updateDate(date);
        }

        if (request.startAt() != null) {
            StartAt startAt = new StartAt(request.startAt());
            reservation.updateStartAt(startAt);
        }

        if (request.endAt() != null) {
            EndAt endAt = new EndAt(request.endAt());
            reservation.updateEndAt(endAt);
        }

        return ReservationResponse.from(reservation);
    }

}
