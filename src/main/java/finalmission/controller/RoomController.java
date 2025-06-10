package finalmission.controller;

import finalmission.dto.request.RoomCreateRequest;
import finalmission.dto.response.RoomCreateResponse;
import finalmission.dto.response.RoomResponse;
import finalmission.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/room")
    public ResponseEntity<RoomCreateResponse> createRoom(
            @RequestBody RoomCreateRequest request
    ) {
        RoomCreateResponse response = roomService.createRoom(request.startDate(), request.endDate(), request.startTime(), request.endTime(), request.isAnonymousRoom());
        URI createdLocation = URI.create("/room/" + response.roomId());
        return ResponseEntity.created(createdLocation).build();
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<RoomResponse> getRoomInfo(
            @PathVariable("roomId") String roomId
    ) {
        RoomResponse response = roomService.getRoomInfo(roomId);
        return ResponseEntity.ok(response);
    }
}
