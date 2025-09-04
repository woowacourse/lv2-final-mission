package finalmission.controller;

import finalmission.dto.AuthenticatedMember;
import finalmission.dto.request.CoachLoginRequest;
import finalmission.dto.request.UpdateMeetingTimeRequest;
import finalmission.dto.response.AllCoachResponse;
import finalmission.dto.response.CoachLoginResponse;
import finalmission.dto.response.CoachResponse;
import finalmission.global.web.Authenticated;
import finalmission.service.CoachService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/coaches/login")
    public CoachLoginResponse login(@RequestBody CoachLoginRequest coachLoginRequest) {
        return coachService.login(coachLoginRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/coaches")
    public List<AllCoachResponse> getAll() {
        return coachService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/coaches/{id}")
    public CoachResponse getById(@PathVariable("id") Long id) {
        return coachService.getById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/admin/coaches/meet-time")
    public void updateMeetingTime(
            @Authenticated AuthenticatedMember authenticatedMember,
            @RequestBody UpdateMeetingTimeRequest updateMeetingTimeRequest
    ) {
        coachService.updateMeetingTime(authenticatedMember.id(), updateMeetingTimeRequest);
    }
}
