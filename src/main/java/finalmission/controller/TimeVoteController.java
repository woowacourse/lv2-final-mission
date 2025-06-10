package finalmission.controller;

import finalmission.dto.*;
import finalmission.service.NameGenerator;
import finalmission.service.TimeVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TimeVoteController {

    private final NameGenerator nameGenerator;
    private final TimeVoteService service;

    @PostMapping("/room")
    public RoomCreateResponse createRoom(
            @RequestBody RoomCreateRequest request
    ) {
        return service.createRoom(request.startDate(), request.endDate(), request.startTime(), request.endTime());
    }

    @GetMapping("/room/{roomId}")
    public RoomResponse getRoomInfo(
            @PathVariable("roomId") String roomId
    ) {
        return service.getRoomInfo(roomId);
    }

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
