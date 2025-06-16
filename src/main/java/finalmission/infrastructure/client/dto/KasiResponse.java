package finalmission.infrastructure.client.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record KasiResponse(Response response) {

    public record Response(Body body) {

    }

    public record Body(Items items) {

    }

    public record Items(List<Item> item) {

    }

    public record Item(
        int locdate,
        String isHoliday
    ) {

        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

        public LocalDate getLocalDate() {
            return LocalDate.parse(String.valueOf(locdate), DATE_FORMATTER);
        }
    }
}
