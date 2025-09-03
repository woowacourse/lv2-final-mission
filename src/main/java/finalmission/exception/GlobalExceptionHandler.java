package finalmission.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    ResponseEntity<String> roomException(ReservationException e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MemberException.class)
    ResponseEntity<String> memberException(MemberException e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ReservationTimeException.class)
    ResponseEntity<String> reservationTimeException(ReservationTimeException e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
