package finalmission.movie.service.member;

import finalmission.member.entity.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.movie.dto.response.MovieReservationCreateResponse;
import finalmission.movie.dto.response.MovieReservationReadResponse;
import finalmission.movie.entity.MovieReservation;
import finalmission.movie.entity.MovieSlot;
import finalmission.movie.repository.MovieReservationRepository;
import finalmission.movie.repository.MovieSlotRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberMovieService {

    private final MovieReservationRepository movieReservationRepository;
    private final MovieSlotRepository movieSlotRepository;
    private final MemberRepository memberRepository;

    public MemberMovieService(
            MovieReservationRepository movieReservationRepository,
            MovieSlotRepository movieSlotRepository,
            MemberRepository memberRepository
    ) {
        this.movieReservationRepository = movieReservationRepository;
        this.movieSlotRepository = movieSlotRepository;
        this.memberRepository = memberRepository;
    }

    public MovieReservationCreateResponse createMovieReservation(Long memberId, Long movieSlotId, Integer seat) {
        Member member = findMemberByIdOrThrow(memberId);
        MovieSlot movieSlot = findMovieSlotByIdOrThrow(movieSlotId);
        MovieReservation reservation = new MovieReservation(member, movieSlot, seat);
        movieReservationRepository.save(reservation);

        return new MovieReservationCreateResponse(
                reservation.getId(),
                member.getName(),
                reservation.getMovieSlot().getMovie().getName(),
                reservation.getSeat()
        );
    }

    private Member findMemberByIdOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("영화 슬롯을 찾을 수 없습니다."));
    }

    private MovieSlot findMovieSlotByIdOrThrow(Long movieSlotId) {
        return movieSlotRepository.findById(movieSlotId)
                .orElseThrow(() -> new IllegalArgumentException("영화 슬롯을 찾을 수 없습니다."));
    }

    public List<MovieReservationReadResponse> readMovieReservation(Long memberId) {
        List<MovieReservation> memberMovieReservations = movieReservationRepository.findByMemberId(memberId);
        return memberMovieReservations.stream()
                .map(this::MovieReservationReadResponse)
                .collect(Collectors.toList());
    }

    private MovieReservationReadResponse MovieReservationReadResponse(MovieReservation movieReservation) {
        return new MovieReservationReadResponse(
                movieReservation.getId(),
                movieReservation.getMovieSlot().getMovie().getName(),
                movieReservation.getMovieSlot().getDate(),
                movieReservation.getMovieSlot().getStartAt(),
                movieReservation.getSeat()
        );
    }
}
