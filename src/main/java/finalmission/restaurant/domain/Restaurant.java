package finalmission.restaurant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, unique = true)
    private String place;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private int maxReservationCount;

    public Restaurant(
            final String name,
            final String description,
            final String place,
            final String phoneNumber,
            final int maxReservationCount
    ) {
        validateName(name);
        validateDescription(description);
        validatePlace(place);
        validatePhoneNumber(phoneNumber);
        validateMaxReservationCount(maxReservationCount);

        this.name = name;
        this.description = description;
        this.place = place;
        this.phoneNumber = phoneNumber;
        this.maxReservationCount = maxReservationCount;
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("음식점 이름은 null 이거나 빈 문자열일 수 없습니다.");
        }
    }

    private void validateDescription(final String description) {
        if (description == null) {
            throw new IllegalArgumentException("음식점 설명은 null이면 안됩니다.");
        }
    }

    private void validatePlace(final String place) {
        if (place == null || place.isBlank()) {
            throw new IllegalArgumentException("장소는 null 이거나 빈 문자열일 수 없습니다.");
        }
    }

    private void validatePhoneNumber(final String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("전화번호는 null 이거나 빈 문자열일 수 없습니다.");
        }
    }

    private void validateMaxReservationCount(final int maxReservationCount) {
        if (maxReservationCount < 0) {
            throw new IllegalArgumentException("최대 예약 수는 0 미만일 수 없습니다.");
        }
    }
}
