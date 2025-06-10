package finalmission.controller.member;

import finalmission.dto.request.MovieReservationCreateRequest;
import finalmission.dto.response.MovieReservationCreateResponse;
import finalmission.service.member.MemberMovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberMovieController {

    private MemberMovieService memberMovieService;

    public MemberMovieController(MemberMovieService memberMovieService) {
        this.memberMovieService = memberMovieService;
    }

    @PostMapping("/movies/reservation")
    public ResponseEntity<MovieReservationCreateResponse> createMovieReservation(
            @RequestBody MovieReservationCreateRequest movieReservationCreateRequest
    ) {
        MovieReservationCreateResponse movieReservationCreateResponse = memberMovieService.createMovieReservation(
                movieReservationCreateRequest.memberName(),
                movieReservationCreateRequest.movieSlotId(),
                movieReservationCreateRequest.seat()
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(movieReservationCreateResponse);
    }
}
