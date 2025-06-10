package finalmission.controller;

import finalmission.dto.RoomCreateResponse;
import finalmission.dto.TimeAddRequest;
import finalmission.dto.TimeResponses;
import finalmission.dto.TimeStaticsResponses;
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
    public RoomCreateResponse createRoom() {
        return service.createRoom();
    }

    @PostMapping("/room/{roomId}")
    public void addTime(
            @PathVariable("roomId") String roomId,
            @RequestBody TimeAddRequest request
    ) {
        String username = request.username() == null ? nameGenerator.generate() : request.username();
        service.addTime(roomId, username, request.values());
    }

    @GetMapping("/room/{roomId}")
    public TimeStaticsResponses getTimeStatics(
            @PathVariable("roomId") String roomId
    ) {
        return service.getTimeStatics(roomId);
    }

    @GetMapping("/room/{roomId}/my")
    public TimeResponses getMyTimes(
            @PathVariable("roomId") String roomId,
            @RequestParam("username") String username
    ) {
        return service.getMyTimes(roomId, username);

    }

    @DeleteMapping("/room/{roomId}")
    public void deleteAllOf(
            @PathVariable("roomId") String roomId,
            @RequestParam("username") String username
    ) {
        service.dropMyTimes(roomId, username);
    }
}
