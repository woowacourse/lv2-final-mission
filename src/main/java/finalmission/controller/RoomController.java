package finalmission.controller;

import finalmission.dto.RoomCreateRequest;
import finalmission.dto.RoomResponse;
import finalmission.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/admin/room")
    public ResponseEntity<RoomResponse> addRoom(@Valid @RequestBody final RoomCreateRequest roomCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addRoom(roomCreateRequest));
    }
}
