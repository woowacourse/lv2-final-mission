package finalmission.room.controller;

import finalmission.global.auth.annotation.RoleRequired;
import finalmission.member.entity.RoleType;
import finalmission.room.dto.request.RoomRequest;
import finalmission.room.dto.response.RoomResponse;
import finalmission.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("rooms")
@Tag(name = "회의실", description = "회의실 관련 API")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "회의실 생성 (어드민 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = RoleType.ADMIN)
    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @RequestBody @Valid RoomRequest request
    ) {
        RoomResponse response = roomService.createRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "모든 회의실 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAllRooms() {
        List<RoomResponse> responses = roomService.findAllRooms();
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "특정 회의실 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findRoomById(
            @PathVariable("id") Long id
    ) {
        RoomResponse response = roomService.findRoomById(id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "회의실 삭제 (어드민 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = RoleType.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable("id") Long id
    ) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
