package finalmission.fixture;

import finalmission.movie.entity.Movie;
import finalmission.movie.entity.MovieSlot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MovieSlotFixture {

    public static MovieSlot create(Movie movie, LocalDate date, LocalTime startAt, Integer seats) {
        return new MovieSlot(movie, date, startAt, seats);
    }

    public static MovieSlot create(Movie movie) {
        return new MovieSlot(movie, LocalDate.now(), LocalTime.now(), 200);
    }

    public static List<MovieSlot> createList(List<Movie> movies, int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> create(movies.get(i)))
                .collect(Collectors.toList());
    }
}
