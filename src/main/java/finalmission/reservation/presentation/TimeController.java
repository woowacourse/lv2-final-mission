package finalmission.reservation.presentation;

import finalmission.reservation.business.TimeService;
import finalmission.reservation.model.Time;
import finalmission.reservation.presentation.dto.request.TimeCreateWebRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservations/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody TimeCreateWebRequest requestBody) {
        Time time = timeService.createTime(requestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(time);
    }

    @GetMapping
    public List<Time> findAll() {
        return timeService.findAllTimes();
    }
}
