package finalmission.service.guest;

import finalmission.dto.response.MovieSlotReadResponse;
import finalmission.entity.MovieSlot;
import finalmission.repository.MovieSlotRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GuestMovieService {

    private MovieSlotRepository movieSlotRepository;

    public GuestMovieService(MovieSlotRepository movieSlotRepository) {
        this.movieSlotRepository = movieSlotRepository;
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
        // TODO: 예약된 좌석 추가하기

        Integer leftSeats = 0;
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
