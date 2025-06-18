package finalmission.reservation.ui;

import finalmission.auth.intercepter.AuthenticationPrincipal;
import finalmission.auth.user.MemberInfo;
import finalmission.reservation.application.ReservationCommandService;
import finalmission.reservation.domain.MailClient;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.vo.ReservationApproval;
import finalmission.reservation.ui.dto.ReservationResponse;
import finalmission.reservation.ui.dto.ReservationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(CoachReservationController.BASE_PATH)
public class CoachReservationController {

    public static final String BASE_PATH = "/coach";

    private final ReservationRepository reservationRepository;

    private final ReservationCommandService reservationCommandService;

    private final MailClient mailClient;

    @PutMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "코치 전용 예약 수정")
    public void updateByCrew(@RequestBody ReservationUpdateRequest request) {
        reservationCommandService.update(request);
    }


    @DeleteMapping("reservations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "코치 전용 예약 삭제")
    public void cancelByCrew(@PathVariable Long id) {
        reservationCommandService.cancel(id);
    }


    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "코치의 모든 예약 조회")
    public List<ReservationResponse> getAllByCoach(@AuthenticationPrincipal MemberInfo memberInfo) {
        return reservationRepository.getAllByCoach(memberInfo.id());
    }

    @PatchMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "예약 승인")
    public void approval(@PathVariable Long id) {
        try {
            ReservationApproval approval = reservationCommandService.approval(id);
            mailClient.send(approval);
        } catch (Exception e) {
            reservationCommandService.waiting(id);
            throw e;
        }
    }
}
