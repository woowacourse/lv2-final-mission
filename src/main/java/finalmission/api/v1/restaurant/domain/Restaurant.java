package finalmission.api.v1.restaurant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ReservationCapacity> reservationCapacities = new ArrayList<>();

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closingTime;

    @Builder
    public Restaurant(final Long id, final String name, final List<ReservationCapacity> reservationCapacities,
                      final String address,
                      final LocalTime openTime, final LocalTime closingTime) {
        this.id = id;
        this.name = name;
        this.reservationCapacities = reservationCapacities;
        this.address = address;
        this.openTime = openTime;
        this.closingTime = closingTime;
    }
}
