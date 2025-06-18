package finalmission.movie.service.member;

import finalmission.global.error.exception.ConflictException;
import finalmission.global.error.exception.ForbiddenException;
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
        validateSlotNotBooked(movieSlotId, seat);

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
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    private MovieSlot findMovieSlotByIdOrThrow(Long movieSlotId) {
        return movieSlotRepository.findById(movieSlotId)
                .orElseThrow(() -> new IllegalArgumentException("영화 슬롯을 찾을 수 없습니다."));
    }

    private void validateSlotNotBooked(Long movieSlotId, Integer seat) {
        if (movieReservationRepository.existsByMovieSlotIdAndSeat(movieSlotId, seat)) {
            throw new ConflictException("이미 좌석이 예약되어 있습니다.");
        }
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

    public void deleteMovieReservation(Long memberId, Long movieReservationId) {
        MovieReservation movieReservation = findMovieReservationByIdOrThrow(movieReservationId);
        validateMovieReservationOwnerMatch(movieReservation, memberId);

        movieReservationRepository.delete(movieReservation);
    }

    private MovieReservation findMovieReservationByIdOrThrow(Long movieReservationId) {
        return movieReservationRepository.findById(movieReservationId)
                .orElseThrow(() -> new IllegalArgumentException("영화 예약을 찾을 수 없습니다."));
    }

    private void validateMovieReservationOwnerMatch(MovieReservation movieReservation, Long requestMemberId) {
        if (!movieReservation.getMember().getId().equals(requestMemberId)) {
            throw new ForbiddenException("영화 예약 주인의 요청이 아닙니다.");
        }
    }
}
