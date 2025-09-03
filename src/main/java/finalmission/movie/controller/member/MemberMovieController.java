package finalmission.movie.controller.member;

import finalmission.global.auth.annotation.AuthenticationPrincipal;
import finalmission.global.auth.annotation.RoleRequired;
import finalmission.global.auth.dto.LoginMember;
import finalmission.member.entity.RoleType;
import finalmission.movie.dto.request.MovieReservationCreateRequest;
import finalmission.movie.dto.response.MovieReservationCreateResponse;
import finalmission.movie.dto.response.MovieReservationReadResponse;
import finalmission.movie.service.member.MemberMovieService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberMovieController {

    private final MemberMovieService memberMovieService;

    public MemberMovieController(MemberMovieService memberMovieService) {
        this.memberMovieService = memberMovieService;
    }

    @PostMapping("/movies/reservation")
    @RoleRequired(roleType = {RoleType.ADMIN, RoleType.USER})
    public ResponseEntity<MovieReservationCreateResponse> createMovieReservation(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestBody MovieReservationCreateRequest movieReservationCreateRequest
    ) {
        MovieReservationCreateResponse movieReservationCreateResponse = memberMovieService.createMovieReservation(
                loginMember.id(),
                movieReservationCreateRequest.movieSlotId(),
                movieReservationCreateRequest.seat()
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(movieReservationCreateResponse);
    }

    @GetMapping("/movies/reservation")
    @RoleRequired(roleType = {RoleType.ADMIN, RoleType.USER})
    public ResponseEntity<List<MovieReservationReadResponse>> readMovieReservation(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        List<MovieReservationReadResponse> movieReservationReadResponses =
                memberMovieService.readMovieReservation(loginMember.id());

        return ResponseEntity.status(HttpStatus.OK.value()).body(movieReservationReadResponses);
    }

    @DeleteMapping("/movies/reservation/{id}")
    @RoleRequired(roleType = RoleType.USER)
    public ResponseEntity<Void> deleteMovieReservation(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long movieReservationId
    ) {
        memberMovieService.deleteMovieReservation(loginMember.id(), movieReservationId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
