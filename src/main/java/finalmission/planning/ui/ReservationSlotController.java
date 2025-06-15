package finalmission.planning.ui;

import finalmission.planning.application.ReservationSlotService;
import finalmission.planning.ui.dto.request.CreateReservationSlotRequest;
import finalmission.planning.ui.dto.response.ReservationSlotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservationSlots")
@RequiredArgsConstructor
public class ReservationSlotController {
    private final ReservationSlotService reservationSlotService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservationSlotResponse createReservationSlot(@RequestBody CreateReservationSlotRequest request) {
        return reservationSlotService.registerReservationSlot(request);
    }
}
