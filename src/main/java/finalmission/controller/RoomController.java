package finalmission.controller;

import finalmission.domain.Room;
import finalmission.dto.MakingRoomRequest;
import finalmission.service.RoomService;
import jakarta.persistence.GeneratedValue;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    ResponseEntity<Room> createRoom(@RequestBody MakingRoomRequest makingRoomRequest) {
        return ResponseEntity.created(URI.create("/room"))
                .body(roomService.createRoom(makingRoomRequest));
    }

    @GetMapping
    ResponseEntity<List<Room>> getAllRooms(){
        return ResponseEntity.ok().body(roomService.getAllRooms());
    }
}
