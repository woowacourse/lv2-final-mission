package finalmission.service.guest;

import finalmission.dto.response.MovieSlotReadResponse;
import finalmission.entity.MovieSlot;
import finalmission.repository.MovieReservationRepository;
import finalmission.repository.MovieSlotRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GuestMovieService {

    private final MovieReservationRepository movieReservationRepository;
    private final MovieSlotRepository movieSlotRepository;

    public GuestMovieService(MovieSlotRepository movieSlotRepository,
                             MovieReservationRepository movieReservationRepository) {
        this.movieSlotRepository = movieSlotRepository;
        this.movieReservationRepository = movieReservationRepository;
    }

    public List<MovieSlotReadResponse> readByMovieIdAndDate(Long movieId, LocalDate date) {
        if (null == date) {
            date = LocalDate.now();
        }
        List<MovieSlot> movieSlots = movieSlotRepository.findByMovieIdAndDate(movieId, date);

        return movieSlots.stream()
                .map(this::convertMovieSlotReadResponse)
                .toList();
    }

    private MovieSlotReadResponse convertMovieSlotReadResponse(MovieSlot movieSlot) {
        Integer leftSeats = movieSlot.getSeats() - movieReservationRepository.countByMovieSlot(movieSlot);
        return new MovieSlotReadResponse(
                movieSlot.getId(),
                movieSlot.getMovie().getName(),
                movieSlot.getDate(),
                movieSlot.getStartAt(),
                movieSlot.getSeats(),
                leftSeats
        );
    }
}
