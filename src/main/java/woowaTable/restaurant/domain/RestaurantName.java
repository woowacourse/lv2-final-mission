package woowaTable.restaurant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantName {

    @Column(name = "name")
    private String value;

    public RestaurantName(final String value) {
        this.value = value;
    }
}
