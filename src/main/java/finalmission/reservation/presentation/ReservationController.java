package finalmission.reservation.presentation;

import finalmission.general.auth.model.LoginInfo;
import finalmission.reservation.business.ReservationService;
import finalmission.reservation.business.dto.request.ReservationCreateRequest;
import finalmission.reservation.business.dto.request.ReservationDeleteRequest;
import finalmission.reservation.business.dto.request.ReservationDetailedGetRequest;
import finalmission.reservation.business.dto.request.ReservationUpdateTreatmentTypeRequest;
import finalmission.reservation.model.Reservation;
import finalmission.reservation.presentation.dto.request.ReservationCreateWebRequest;
import finalmission.reservation.presentation.dto.request.ReservationUpdateTreatmentTypeWebRequest;
import finalmission.reservation.presentation.dto.response.ReservationGetDetailWebResponse;
import finalmission.reservation.presentation.dto.response.ReservationGetWebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationGetDetailWebResponse> create(@RequestBody ReservationCreateWebRequest requestBody, LoginInfo loginInfo) {
        Reservation reservation = reservationService.createReservation(new ReservationCreateRequest(requestBody.treatmentType(), requestBody.date(), requestBody.timeId(), loginInfo.username()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReservationGetDetailWebResponse(reservation.getId(), reservation.getMember().getName(), reservation.getTreatmentType(), reservation.getDate(), reservation.getTime().getStartAt(), reservation.getCreatedAt()));
    }

    @GetMapping
    public List<ReservationGetWebResponse> findAll() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return reservations.stream()
                .map(reservation -> new ReservationGetWebResponse(reservation.getId(), reservation.getTreatmentType(), reservation.getDate(), reservation.getTime().getStartAt()))
                .toList();
    }

    @GetMapping("/period")
    public List<ReservationGetWebResponse> findReservationsOfPeriod(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Reservation> reservations = reservationService.findReservationOfPeriod(startDate, endDate);
        return reservations.stream()
                .map(reservation -> new ReservationGetWebResponse(reservation.getId(), reservation.getTreatmentType(), reservation.getDate(), reservation.getTime().getStartAt()))
                .toList();
    }

    @GetMapping("/member/{name}")
    public List<ReservationGetWebResponse> findMemberReservations(@PathVariable String name) {
        List<Reservation> reservations = reservationService.findMemberReservations(name);
        return reservations.stream()
                .map(reservation -> new ReservationGetWebResponse(reservation.getId(), reservation.getTreatmentType(), reservation.getDate(), reservation.getTime().getStartAt()))
                .toList();
    }

    @GetMapping("/{id}")
    public ReservationGetDetailWebResponse findMyReservationDetail(@PathVariable Long id, LoginInfo loginInfo) {
        Reservation reservation = reservationService.findDetailedReservationOfMember(new ReservationDetailedGetRequest(id, loginInfo.username()));
        return new ReservationGetDetailWebResponse(reservation.getId(), reservation.getMember().getName(), reservation.getTreatmentType(), reservation.getDate(), reservation.getTime().getStartAt(), reservation.getCreatedAt());
    }

    @PatchMapping("/{id}")
    public ReservationGetWebResponse update(@PathVariable Long id, @RequestBody ReservationUpdateTreatmentTypeWebRequest requestBody, LoginInfo loginInfo) {
        Reservation reservation = reservationService.changeTreatmentType(new ReservationUpdateTreatmentTypeRequest(id, requestBody.treatmentType(), loginInfo.username()));
        return new ReservationGetWebResponse(reservation.getId(), reservation.getTreatmentType(), reservation.getDate(), reservation.getTime().getStartAt());
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, LoginInfo loginInfo) {
       reservationService.deleteById(new ReservationDeleteRequest(id, loginInfo.username()));
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }
}
