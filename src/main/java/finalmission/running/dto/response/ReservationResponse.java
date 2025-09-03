package finalmission.running.dto.response;

import finalmission.member.domain.Member;
import finalmission.running.domain.Participant;
import finalmission.running.domain.RunningSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(
    Long id,
    String creator,
    LocalDate date,
    LocalTime startAt,
    LocalTime endTime,
    List<String> participants
) {

    public static ReservationResponse from(RunningSession runningSession) {
        return new ReservationResponse(
            runningSession.getId(),
            runningSession.getCreator().getNickname(),
            runningSession.getDate(),
            runningSession.getStartAt(),
            runningSession.getEndTime(),
            runningSession.getParticipants().stream()
                .map(Participant::getMember)
                .map(Member::getNickname)
                .toList());
    }
}
