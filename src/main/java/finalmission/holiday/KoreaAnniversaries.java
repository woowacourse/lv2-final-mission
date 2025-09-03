package finalmission.holiday;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public record KoreaAnniversaries(
        Response response
) {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    protected record Response(Header header, Body body) {
    }

    protected record Header(String resultCode, String resultMsg) {
    }

    protected record Body(Items items, Long numOfRows, Long pageNo, Long totalCount) {
    }

    protected record Items(@JsonProperty("item") List<Item> items) {
    }

    protected record Item(String dateKind, String dateName, String isHoliday, Long locdate, Long seq) {
    }

    public boolean isHoliday(LocalDate date) {
        String formattedDate = date.format(formatter);
        Long numberDate = Long.parseLong(formattedDate);

        Optional<Item> target =
                response
                        .body
                        .items
                        .items.stream()
                        .filter(item -> item.locdate.equals(numberDate))
                        .findFirst();

        return target.map(item -> item.isHoliday.equalsIgnoreCase("Y")).orElse(false);
    }
}
