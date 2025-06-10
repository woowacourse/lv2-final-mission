package finalmission.dto;

import finalmission.domain.Lecture;
import finalmission.domain.Reservation;
import java.time.LocalDate;

public record ReservationMineResponse(Long id, LocalDate reservedAt, LectureResponse lecture) {

    public static ReservationMineResponse from(Reservation reservation) {
        Lecture lecture = reservation.lecture();
        return new ReservationMineResponse(
                reservation.id(),
                reservation.reservedAt(),
                new LectureResponse(lecture.sport(), lecture.date())
        );
    }
}
