package finalmission.service.admin;

import finalmission.dto.response.MovieCreateResponse;
import finalmission.dto.response.MovieSlotCreateResponse;
import finalmission.entity.Movie;
import finalmission.entity.MovieSlot;
import finalmission.repository.MovieRepository;
import finalmission.repository.MovieSlotRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Service;

@Service
public class AdminMovieService {

    private final MovieRepository movieRepository;
    private final MovieSlotRepository movieSlotRepository;

    public AdminMovieService(MovieRepository movieRepository, MovieSlotRepository movieSlotRepository) {
        this.movieRepository = movieRepository;
        this.movieSlotRepository = movieSlotRepository;
    }

    public MovieCreateResponse createMovie(String name, String description) {
        Movie movie = new Movie(name, description);
        movieRepository.save(movie);
        return new MovieCreateResponse(movie.getId(), movie.getName(), movie.getDescription());
    }

    public MovieSlotCreateResponse createMovieSlot(Long movieId, LocalDate date, LocalTime startAt, Long seats) {
        Movie movie = findMovieByIdOrThrow(movieId);
        MovieSlot movieSlot = new MovieSlot(movie, date, startAt, seats);
        movieSlotRepository.save(movieSlot);
        return new MovieSlotCreateResponse(
                movieSlot.getId(), movie.getName(), movieSlot.getDate(), movieSlot.getStartAt(), movieSlot.getSeats());
    }

    private Movie findMovieByIdOrThrow(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("영화를 찾을 수 없습니다."));
    }
}
