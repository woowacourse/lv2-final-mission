package finalmission.ballparkreservation.external.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record HolidayResponse(
        Response response
) {

    static class Response {
        Body body;

        public Body getBody() {
            return body;
        }

        static class Body {
            List<Item> items;

            public List<Item> getItems() {
                return items;
            }

            static class Item {
                String date;

                public String getDate() {
                    return date;
                }
            }
        }
    }

    public List<LocalDate> getHolidays() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return this.response.getBody().items.stream()
                .map(item -> LocalDate.parse(item.getDate(), formatter))
                .toList();
    }
}
