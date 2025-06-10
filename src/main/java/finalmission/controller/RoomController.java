package finalmission.controller;

import finalmission.dto.request.RoomCreateRequest;
import finalmission.dto.response.RoomCreateResponse;
import finalmission.dto.response.RoomResponse;
import finalmission.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

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
}
