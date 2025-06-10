package finalmission.service.member;

import finalmission.dto.response.MovieReservationCreateResponse;
import finalmission.entity.MovieReservation;
import finalmission.entity.MovieSlot;
import finalmission.repository.MovieReservationRepository;
import finalmission.repository.MovieSlotRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberMovieService {

    private MovieReservationRepository movieReservationRepository;
    private MovieSlotRepository movieSlotRepository;

    public MemberMovieService(MovieReservationRepository movieReservationRepository,
                              MovieSlotRepository movieSlotRepository) {
        this.movieReservationRepository = movieReservationRepository;
        this.movieSlotRepository = movieSlotRepository;
    }

    public MovieReservationCreateResponse createMovieReservation(String memberName, Long movieSlotId, Integer seat) {
        MovieSlot movieSlot = findMovieSlotByIdOrThrow(movieSlotId);
        MovieReservation reservation = new MovieReservation(memberName, movieSlot, seat);
        movieReservationRepository.save(reservation);

        return new MovieReservationCreateResponse(
                reservation.getId(),
                reservation.getMemberName(),
                reservation.getMovieSlot().getMovie().getName(),
                reservation.getSeat()
        );
    }

    private MovieSlot findMovieSlotByIdOrThrow(Long movieSlotId) {
        return movieSlotRepository.findById(movieSlotId)
                .orElseThrow(() -> new IllegalArgumentException("영화 슬롯을 찾을 수 없습니다."));
    }
}
