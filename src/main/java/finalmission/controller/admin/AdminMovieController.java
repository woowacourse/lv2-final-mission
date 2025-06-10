package finalmission.controller.admin;

import finalmission.dto.request.MovieCreateRequest;
import finalmission.service.admin.AdminMovieService;
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

    // 영화 추가
    @PostMapping("/movies")
    public ResponseEntity<Void> createMovie(@RequestBody MovieCreateRequest movieCreateRequest) {
        adminMovieService.createMovie(movieCreateRequest.name(), movieCreateRequest.description());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
