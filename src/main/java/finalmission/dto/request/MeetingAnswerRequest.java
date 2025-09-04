package finalmission.dto.request;

import finalmission.domain.MeetingStatus;

public record MeetingAnswerRequest(
        MeetingStatus answer
) {

}
