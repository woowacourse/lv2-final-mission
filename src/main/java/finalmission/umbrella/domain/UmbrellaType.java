package finalmission.umbrella.domain;

import lombok.Getter;

@Getter
public enum UmbrellaType {
    SMALL(500),
    BIG(1000);

    private final long price;

    UmbrellaType(long price) {
        this.price = price;
    }
}
