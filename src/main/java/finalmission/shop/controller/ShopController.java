package finalmission.shop.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finalmission.auth.UserId;
import finalmission.shop.dto.ReservationResponse;
import finalmission.shop.dto.ShopResponse;
import finalmission.shop.service.ShopService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<List<ShopResponse.Simple>> getAll() {
        return ResponseEntity.ok(shopService.getAll());
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopResponse.Detail> getDetail(
            @PathVariable(name = "shopId") Long shopId
    ) {
        return ResponseEntity.ok(shopService.getDetail(shopId));
    }

    @GetMapping("/{shopId}/times")
    public ResponseEntity<List<LocalTime>> getAvailableTime(
            @PathVariable(name = "shopId") Long shopId,
            @RequestParam(name = "date") LocalDate date
    ) {
        return ResponseEntity.ok(shopService.getAvailableTime(shopId, date));
    }

    @PostMapping("/{shopId}")
    public ResponseEntity<ReservationResponse.Created> reserve(
            @UserId Long userId,
            @PathVariable(name = "shopId") Long shopId,
            @RequestParam(name = "date") LocalDate date,
            @RequestParam(name = "time") LocalTime time
    ) {
        return ResponseEntity.ok(shopService.reserve(userId, shopId, date, time));
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> cancel(
            @UserId Long userId,
            @RequestParam(name = "reservationId") Long reservationId
    ) {
        shopService.cancel(userId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ReservationResponse.Simple>> myReservations(
            @UserId Long userId
    ) {
        return ResponseEntity.ok(shopService.getMyReservations(userId));
    }
}
