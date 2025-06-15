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
            Items items;

            public Items getItems() {
                return items;
            }

            static class Items {
                List<Item> item;

                public List<Item> getItem() {
                    return item;
                }

                static class Item {
                    String locdate;

                    public String getLocdate() {
                        return locdate;
                    }
                }
            }
        }
    }

    public List<LocalDate> getHolidays() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return this.response.getBody().getItems().getItem().stream()
                .map(info -> LocalDate.parse(info.getLocdate(), formatter))
                .toList();
    }
}
