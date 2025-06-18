package finalmission.movie.repository;

import finalmission.member.entity.Member;
import finalmission.movie.entity.MovieReservation;
import finalmission.movie.entity.MovieSlot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReservationRepository extends JpaRepository<MovieReservation, Long> {

    List<MovieReservation> findByMemberId(Long memberId);

    Integer countByMovieSlot(MovieSlot movieSlot);

    boolean existsByMovieSlotIdAndSeat(Long movieSlotId, Integer seat);
}
