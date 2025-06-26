package finalmission.reservation.business.dto.request;

public record ReservationDetailedGetRequest(Long id, String username) {

    public ReservationDetailedGetRequest {
        if (id == null) {
            throw new IllegalArgumentException("id는 null이 될 수 없습니다.");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("예약자 아이디는 null이 될 수 없습니다.");
        }
    }
}
