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
        String name = request.name() == null ? nameGenerator.generate() : request.name();
        service.addTime(roomId, name, request.values());
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
            @RequestParam("name") String name
    ) {
        return service.getMyTimes(roomId, name);
    }

    @DeleteMapping("/time/{roomId}")
    public void deleteAllTimeOf(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name
    ) {
        service.dropMyTimes(roomId, name);
    }
}
