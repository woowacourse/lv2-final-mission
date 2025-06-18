package finalmission.reservation.application.dto;

import finalmission.meetingRoom.application.dto.MeetingRoomResponse;
import finalmission.member.application.dto.MemberResponse;
import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(Long id, MemberResponse member, MeetingRoomResponse meetingRoom, LocalDate date,
                                  LocalTime startAt, LocalTime endAt) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), MemberResponse.from(reservation.getMember()),
                MeetingRoomResponse.from(reservation.getMeetingRoom()), reservation.getDate(), reservation.getStartAt(),
                reservation.getEndAt());
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
