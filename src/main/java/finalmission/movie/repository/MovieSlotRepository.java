package finalmission.movie.repository;

import finalmission.movie.entity.MovieSlot;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieSlotRepository extends JpaRepository<MovieSlot, Long> {

    @Query("""
            SELECT ms
            FROM MovieSlot ms
            JOIN FETCH Movie m On ms.movie.id = m.id
            WHERE (:movieId is null or ms.movie.id = :movieId) AND
                       (:date is null or ms.date = :date)
            """
    )
    List<MovieSlot> findByMovieIdAndDate(Long movieId, LocalDate date);
}
