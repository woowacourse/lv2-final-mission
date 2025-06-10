package finalmission.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record Item(
        @JsonProperty("locdate")
        LocalDate locdate
) {
}
