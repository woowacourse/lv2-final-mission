package finalmission.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Holidays {

    @JsonProperty("items")
    private List<Item> items;

    public Holidays(final List<Item> items) {
        this.items = items;
    }

    public List<Item> getHolidays() {
        return items;
    }
}
