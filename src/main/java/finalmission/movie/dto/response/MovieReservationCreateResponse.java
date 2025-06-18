package finalmission.movie.dto.response;

public record MovieReservationCreateResponse(Long id, String memberName, String movieName, Integer seats) {
}
