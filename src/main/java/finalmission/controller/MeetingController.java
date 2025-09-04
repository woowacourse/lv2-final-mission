package finalmission.controller;

import finalmission.dto.AuthenticatedMember;
import finalmission.dto.request.CreateMeetingRequest;
import finalmission.dto.request.MeetingAnswerRequest;
import finalmission.dto.request.UpdateMeetingRequest;
import finalmission.dto.response.AllMeetingResponse;
import finalmission.dto.response.MeetingAppliedCrewResponse;
import finalmission.dto.response.MeetingResponse;
import finalmission.global.web.Authenticated;
import finalmission.service.MeetingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/meetings")
    public void create(
            @Authenticated AuthenticatedMember authenticatedMember,
            @RequestBody CreateMeetingRequest createMeetingRequest
    ) {
        meetingService.create(authenticatedMember.id(), createMeetingRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/coaches/meetings")
    public List<MeetingAppliedCrewResponse> getAllMeetingApplicantByCoach(
            @Authenticated AuthenticatedMember authenticatedMember
    ) {
        return meetingService.getAllMeetingApplicantByCoachId(authenticatedMember.id());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/crews/meetings")
    public List<AllMeetingResponse> getAllByCrewId(@Authenticated AuthenticatedMember authenticatedMember) {
        return meetingService.getAllByCrewId(authenticatedMember.id());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/meetings/{meetingId}")
    public MeetingResponse getByIdAndCrewId(
            @PathVariable("meetingId") Long meetingId,
            @Authenticated AuthenticatedMember authenticatedMember
    ) {
        return meetingService.getByIdAndCrewId(meetingId, authenticatedMember.id());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/user/meetings/{meetingId}")
    public void update(
            @PathVariable("meetingId") Long meetingId,
            @Authenticated AuthenticatedMember authenticatedMember,
            @RequestBody UpdateMeetingRequest updateMeetingRequest
    ) {
        meetingService.update(meetingId, authenticatedMember.id(), updateMeetingRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/admin/meetings/{meetingId}")
    public void answer(
            @PathVariable("meetingId") Long meetingId,
            @RequestBody MeetingAnswerRequest meetingAnswerRequest
    ) {
        meetingService.updateAnswer(meetingId, meetingAnswerRequest);
    }
}
