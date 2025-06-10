package finalmission.controller;

import finalmission.dto.LoginMemberInfo;
import finalmission.dto.ReservationMineResponse;
import finalmission.dto.ReservationRequest;
import finalmission.dto.ReservationResponse;
import finalmission.service.ReservationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findReservations() {
        log.info("전체 예약 목록 조회 요청 수신");
        List<ReservationResponse> response = reservationService.findReservations();

        log.info("전체 예약 목록 조회 완료: 총 {}건", response.size());
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> saveReservation(@RequestBody @Valid ReservationRequest request, LoginMemberInfo memberInfo) {
        log.info("예약 요청 수신: reservationId={}, memberId={}", request.lectureId(), memberInfo.id());
        ReservationResponse response = reservationService.saveReservation(request, memberInfo.id());

        log.info("예약 생성 완료: reservationId={}", response.id());
        return ResponseEntity.created(URI.create("/reservation"))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable("id") long id) {
        log.info("예약 삭제 요청 수신: reservationId={}", id);
        reservationService.deleteReservationById(id);

        log.info("예약 삭제 완료: reservationId={}", id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReservationMineResponse>> findMyReservations(LoginMemberInfo memberInfo) {
        log.info("나의 예약 목록 조회 요청: memberId={}", memberInfo.id());
        List<ReservationMineResponse> response = reservationService.findReservationsOfMember(memberInfo.id());

        log.debug("조회된 예약/대기 수: {}", response.size());
        return ResponseEntity.ok()
                .body(response);
    }
}
