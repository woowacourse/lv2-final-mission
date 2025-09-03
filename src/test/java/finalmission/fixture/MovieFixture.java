package finalmission.fixture;

import finalmission.movie.entity.Movie;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MovieFixture {

    public static Movie create(String name, String description) {
        return new Movie(name, description);
    }

    public static Movie createDefault() {
        return create(generateRandomString(10), generateRandomString(15));
    }

    public static List<Movie> createDefaultList(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> createDefault())
                .collect(Collectors.toList());
    }

    private static String generateRandomString(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }
}
