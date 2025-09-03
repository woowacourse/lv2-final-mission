package finalmission.movie.service.guest;

import finalmission.holiday.service.HolidayCheckService;
import finalmission.movie.dto.response.MovieSlotReadResponse;
import finalmission.movie.entity.MovieSlot;
import finalmission.movie.repository.MovieReservationRepository;
import finalmission.movie.repository.MovieSlotRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GuestMovieService {

    private final MovieReservationRepository movieReservationRepository;
    private final MovieSlotRepository movieSlotRepository;
    private final HolidayCheckService holidayCheckService;

    public GuestMovieService(
            MovieSlotRepository movieSlotRepository,
            MovieReservationRepository movieReservationRepository,
            HolidayCheckService holidayCheckService
    ) {
        this.movieSlotRepository = movieSlotRepository;
        this.movieReservationRepository = movieReservationRepository;
        this.holidayCheckService = holidayCheckService;
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
                holidayCheckService.isHoliday(movieSlot.getDate()),
                movieSlot.getSeats(),
                leftSeats
        );
    }
}
