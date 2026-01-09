package finalmission.reservation;

import finalmission.meetingroom.MeetingRoom;
import finalmission.meetingroom.MeetingRoomRepository;
import finalmission.member.Member;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    public List<MeetingRoomTimeResponse> getAvailableTimes(Long meetingRoomId,
        LocalDate date) {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        MeetingRoom meetingRoom = meetingRoomRepository.findById(meetingRoomId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회의실입니다."));
        List<Reservation> reservations = reservationRepository.findByMeetingRoomAndDate(
            meetingRoom, date);
        return reservationTimes.stream()
            .map(reservationTime ->
                new MeetingRoomTimeResponse(
                    reservationTime.getId(),
                    reservationTime.getStartAt(),
                    hasReservationInTime(reservationTime, reservations)
                )).toList();
    }

    private static boolean hasReservationInTime(ReservationTime reservationTime,
        List<Reservation> reservations) {
        return reservations.stream()
            .anyMatch(reservation ->
                reservation.isReservationTime(reservationTime));
    }

    public void create(Member member, ReservationRequest request) {
        validateHasReservation(request.meetingRoomId(), request.date(), request.timeId());
        MeetingRoom meetingRoom = meetingRoomRepository.findById(request.meetingRoomId())
            .orElseThrow(() -> new IllegalArgumentException("해당하는 회의실이 없습니다."));
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
            .orElseThrow(() -> new IllegalArgumentException("해당하는 시간이 없습니다."));

        Reservation reservation = new Reservation(
            member, meetingRoom, reservationTime, request.date()
        );

        reservationRepository.save(reservation);
    }

    public List<ReservationResponse> getByMember(Member member) {
        return reservationRepository.findAllByMemberId(member.getId())
            .stream().map(reservation -> new ReservationResponse(
                reservation.getId(),
                reservation.getMeetingRoom().getName(),
                reservation.getTime().getStartAt(),
                reservation.getTime().getStartAt().plusHours(1),
                reservation.getDate()
            )).toList();
    }

    public void delete(Member member, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 예약이 없습니다."));

        validateIsMy(reservation, member);

        reservationRepository.delete(reservation);
    }

    private void validateHasReservation(Long meetingRoomId, LocalDate date, Long timeId) {
        if (reservationRepository.existsByMeetingRoomIdAndDateAndTimeId(meetingRoomId, date,
            timeId)) {
            throw new IllegalArgumentException("해당 시간에 예약이 존재합니다");
        }
    }

    private void validateIsMy(Reservation reservation, Member member) {
        if (!reservation.isBy(member)) {
            throw new IllegalArgumentException("나의 예약만 삭제 가능합니다.");
        }
    }
}
