package finalmission.dto.request;

public record MovieReservationCreateRequest(String memberName, Long movieSlotId, Integer seat) {
}
