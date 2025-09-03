package finalmission.movie.controller.guest;

import finalmission.movie.dto.response.MovieSlotReadResponse;
import finalmission.movie.service.guest.GuestMovieService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestMovieController {

    private GuestMovieService guestMovieService;

    public GuestMovieController(GuestMovieService guestMovieService) {
        this.guestMovieService = guestMovieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieSlotReadResponse>> readMovieSlot(
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) LocalDate date
    ) {
        List<MovieSlotReadResponse> movieSlotReadResponses =
                guestMovieService.readByMovieIdAndDate(movieId, date);

        return ResponseEntity.status(HttpStatus.OK).body(movieSlotReadResponses);
    }
}
