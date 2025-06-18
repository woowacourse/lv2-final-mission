package finalmission.seat.controller;

import finalmission.member.auth.annotation.RoleRequired;
import finalmission.member.domain.Role;
import finalmission.seat.controller.dto.SeatRequest;
import finalmission.seat.controller.dto.SeatResponse;
import finalmission.seat.repository.vo.SeatWithReserved;
import finalmission.seat.service.SeatFrontService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatFrontService seatFrontService;

    @RoleRequired(value = Role.ADMIN)
    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(@RequestBody final SeatRequest request) {
        return ResponseEntity.ok(seatFrontService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getSeat(@PathVariable final Long id) {
        return ResponseEntity.ok(seatFrontService.get(id));
    }

    @GetMapping("/concert")
    public ResponseEntity<List<SeatWithReserved>> getSeats(@RequestParam final Long concertId) {
        return ResponseEntity.ok(seatFrontService.getSeatsWithReserved(concertId));
    }

    @RoleRequired(value = Role.ADMIN)
    @GetMapping
    public List<SeatResponse> getAllSeat() {
        return seatFrontService.getAll();
    }
}
