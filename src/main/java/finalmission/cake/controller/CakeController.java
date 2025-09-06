package finalmission.cake.controller;

import finalmission.cake.dto.AvailableTimeResponse;
import finalmission.cake.dto.BasicCakeResponse;
import finalmission.cake.dto.CakeDetailsResponse;
import finalmission.cake.dto.CakeReservationRequest;
import finalmission.cake.dto.CakeReservationResponse;
import finalmission.cake.dto.MemberCakesResponse;
import finalmission.cake.service.CakeService;
import finalmission.exception.UnauthorizedException;
import finalmission.member.jwt.TokenProvider;
import finalmission.util.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CakeController {

    private final CakeService cakeService;
    private final TokenProvider tokenProvider;

    @GetMapping("/cakes")
    public ResponseEntity<List<BasicCakeResponse>> findAll() {
        List<BasicCakeResponse> response = cakeService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cakes/detail")
    public ResponseEntity<CakeDetailsResponse> findDetails() {
        CakeDetailsResponse response = cakeService.findDetails();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cakes/{id}")
    public ResponseEntity<List<AvailableTimeResponse>> findAvailableTimeByDate(@PathVariable Long id,
                                                                               @RequestParam @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate date) {
        List<AvailableTimeResponse> response = cakeService.findCakeAvailableTime(id, date);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cakes")
    public ResponseEntity<CakeReservationResponse> createCakeReservation(
            @Valid @RequestBody CakeReservationRequest request,
            HttpServletRequest servletRequest) {
        Long memberId = getMemberId(servletRequest);
        CakeReservationResponse response = cakeService.create(request, memberId);
        return ResponseEntity.created(URI.create("/cakes/" + response.reservationId())).body(response);
    }

    @GetMapping("/members/cakes")
    public ResponseEntity<List<MemberCakesResponse>> getMemberCakeReservations(HttpServletRequest request) {
        Long memberId = getMemberId(request);
        List<MemberCakesResponse> responses = cakeService.getMemberCakeReservations(memberId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/cakes/{id}")
    public ResponseEntity<Void> deleteCakeReservation(@PathVariable(name = "id") Long reservationId,
                                                      HttpServletRequest request) {
        Long memberId = getMemberId(request);
        cakeService.deleteCakeReservation(reservationId, memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cakes/{id}")
    public ResponseEntity<CakeReservationResponse> updateCakeReservation(@PathVariable(name = "id") Long reservationId,
                                                                         @RequestBody CakeReservationRequest cakeReservationRequest,
                                                                         HttpServletRequest request) {
        Long memberId = getMemberId(request);
        CakeReservationResponse response = cakeService.updateReservation(memberId, reservationId, cakeReservationRequest);
        return ResponseEntity.ok(response);
    }

    private Long getMemberId(HttpServletRequest servletRequest) {
        Optional<String> optionalToken = CookieManager.extractByName("authorization", servletRequest);
        if (optionalToken.isPresent()) {
            String authorization = optionalToken.get();
            return tokenProvider.getMemberIdFromToken(authorization);
        }
        throw UnauthorizedException.jwtTokenInvalid();
    }
}
