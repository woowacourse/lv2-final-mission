package lavatoryreservation.external.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        log.debug(exception.getMessage());
        System.out.println(exception.getMessage());

        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
