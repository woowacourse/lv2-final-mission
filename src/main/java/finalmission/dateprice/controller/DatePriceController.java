package finalmission.dateprice.controller;

import finalmission.dateprice.dto.AddDatePriceRequest;
import finalmission.dateprice.dto.DatePriceResponse;
import finalmission.dateprice.service.DatePriceService;
import finalmission.global.auth.LoginAdmin;
import finalmission.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/date-price")
public class DatePriceController {

    private final DatePriceService datePriceService;

    public DatePriceController(DatePriceService datePriceService) {
        this.datePriceService = datePriceService;
    }

    @PostMapping
    public ResponseEntity<DatePriceResponse> add(@LoginAdmin Member admin, @RequestBody AddDatePriceRequest request) {
        DatePriceResponse response = datePriceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
