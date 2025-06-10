package finalmission.service.admin;

import finalmission.entity.Movie;
import finalmission.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminMovieService {

    private final MovieRepository movieRepository;

    public AdminMovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovie(String name, String description) {
        Movie movie = new Movie(name, description);
        movieRepository.save(movie);
    }
}
