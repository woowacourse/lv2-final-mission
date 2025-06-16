package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record ReservationDetails(
    @Column(nullable = false)
    String crew,

    @Column(nullable = false)
    String description
) { // TODO. 필드 사이즈 제한 추가

    public ReservationDetails {
        if (crew == null || crew.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 필수입니다.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("예약 설명은 필수입니다.");
        }
    }
}
