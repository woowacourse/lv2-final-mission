package finalmission.controller;

import finalmission.domain.Voter;
import finalmission.dto.request.TimeAddRequest;
import finalmission.dto.response.TimeResponses;
import finalmission.dto.response.TimeStaticsResponses;
import finalmission.service.TimeService;
import finalmission.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TimeController {

    private final VoterService voterService;
    private final TimeService timeService;

    @PostMapping("/time/{roomId}")
    public void addTime(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestBody TimeAddRequest request
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        timeService.addTime(roomId, voter, request.values());
    }

    @GetMapping("/time/{roomId}")
    public TimeStaticsResponses getTimeStatics(
            @PathVariable("roomId") String roomId
    ) {
        return timeService.getTimeStatics(roomId);
    }

    @GetMapping("/time/{roomId}/my")
    public TimeResponses getMyTimes(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        return timeService.getMyTimes(roomId, voter);
    }

    @DeleteMapping("/time/{roomId}")
    public void deleteAllTimeOf(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        timeService.dropMyTimes(roomId, voter);
    }
}
