package finalmission.domain.restaurant;

import finalmission.domain.restaurant.detail.RestaurantDetail;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private RestaurantDetail detail;

    protected Restaurant() {
    }
}
