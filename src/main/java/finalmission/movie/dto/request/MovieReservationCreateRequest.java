package finalmission.movie.dto.request;

public record MovieReservationCreateRequest(String memberName, Long movieSlotId, Integer seat) {
}
