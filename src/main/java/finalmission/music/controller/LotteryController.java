package finalmission.music.controller;

import finalmission.music.controller.dto.LotteryRequest;
import finalmission.music.controller.dto.LotteryResponse;
import finalmission.music.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryService lotteryService;

    @PostMapping("/lotteries")
    public ResponseEntity<LotteryResponse> create(@RequestBody LotteryRequest request) {
        LotteryResponse response = lotteryService.createLottery(request.albumId(), request.expirationDateTime());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
