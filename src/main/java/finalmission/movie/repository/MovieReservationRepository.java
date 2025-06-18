package finalmission.movie.repository;

import finalmission.movie.entity.MovieReservation;
import finalmission.movie.entity.MovieSlot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReservationRepository extends JpaRepository<MovieReservation, Long> {

    List<MovieReservation> findByMemberName(String memberName);

    Integer countByMovieSlot(MovieSlot movieSlot);
}
