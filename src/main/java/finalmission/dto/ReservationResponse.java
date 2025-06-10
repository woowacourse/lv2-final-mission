package finalmission.dto;

import finalmission.domain.Lecture;
import finalmission.domain.Member;
import finalmission.domain.Reservation;

public record ReservationResponse(Long id, LectureResponse lecture, MemberResponse member) {

    public static ReservationResponse from(Reservation reservation) {
        Lecture lecture = reservation.lecture();
        Member member = reservation.member();
        return new ReservationResponse(
                reservation.id(),
                new LectureResponse(lecture.sport(), lecture.date()),
                new MemberResponse(member.name())
        );
    }
}
