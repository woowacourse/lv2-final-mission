package finalmission.controller;

import finalmission.dto.request.TimeAddRequest;
import finalmission.dto.response.TimeResponses;
import finalmission.dto.response.TimeStaticsResponses;
import finalmission.service.NameGenerator;
import finalmission.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TimeController {

    private final NameGenerator nameGenerator;
    private final TimeService service;

    @PostMapping("/time/{roomId}")
    public void addTime(
            @PathVariable("roomId") String roomId,
            @RequestBody TimeAddRequest request
    ) {
        String username = request.username() == null ? nameGenerator.generate() : request.username();
        service.addTime(roomId, username, request.values());
    }

    @GetMapping("/time/{roomId}")
    public TimeStaticsResponses getTimeStatics(
            @PathVariable("roomId") String roomId
    ) {
        return service.getTimeStatics(roomId);
    }

    @GetMapping("/time/{roomId}/my")
    public TimeResponses getMyTimes(
            @PathVariable("roomId") String roomId,
            @RequestParam("username") String username
    ) {
        return service.getMyTimes(roomId, username);

    }

    @DeleteMapping("/time/{roomId}")
    public void deleteAllTimeOf(
            @PathVariable("roomId") String roomId,
            @RequestParam("username") String username
    ) {
        service.dropMyTimes(roomId, username);
    }
}
