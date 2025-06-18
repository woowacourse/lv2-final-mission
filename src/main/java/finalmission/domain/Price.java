package finalmission.domain;

import lombok.Getter;

@Getter
public enum Price {
    WEEKDAY(10_000),
    HOLIDAY(15_000);

    private final int price;

    Price(final int price) {
        this.price = price;
    }
}
