package finalmission.movie.controller.admin;

import finalmission.global.auth.annotation.RoleRequired;
import finalmission.member.entity.RoleType;
import finalmission.movie.dto.request.MovieCreateRequest;
import finalmission.movie.dto.request.MovieSlotCreateRequest;
import finalmission.movie.dto.response.MovieCreateResponse;
import finalmission.movie.dto.response.MovieSlotCreateResponse;
import finalmission.movie.service.admin.AdminMovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminMovieController {

    private AdminMovieService adminMovieService;

    public AdminMovieController(AdminMovieService adminMovieService) {
        this.adminMovieService = adminMovieService;
    }

    @PostMapping("/movies")
    @RoleRequired(roleType = RoleType.ADMIN)
    public ResponseEntity<MovieCreateResponse> createMovie(@RequestBody MovieCreateRequest movieCreateRequest) {
        MovieCreateResponse movieCreateResponse = adminMovieService.createMovie(
                movieCreateRequest.name(), movieCreateRequest.description());

        return ResponseEntity.status(HttpStatus.CREATED).body(movieCreateResponse);
    }

    @PostMapping("/movie-slots")
    @RoleRequired(roleType = RoleType.ADMIN)
    public ResponseEntity<MovieSlotCreateResponse> createMovieSlot(
            @RequestBody MovieSlotCreateRequest movieSlotCreateRequest
    ) {
        MovieSlotCreateResponse movieSlotCreateResponse = adminMovieService.createMovieSlot(
                movieSlotCreateRequest.movieId(),
                movieSlotCreateRequest.date(),
                movieSlotCreateRequest.startAt(),
                movieSlotCreateRequest.seats()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(movieSlotCreateResponse);
    }
}
