package finalmission.reservation.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ConfirmHolidayResponse(Response response) {

    public record Response(Header header, Body body) {}

    public record Header(String resultCode, String resultMsg) {}

    public record Body(Items items, int numOfRows, int pageNo, int totalCount) {}

    public record Items(@JsonProperty("item") List<Item> itemList) {}

    public record Item(long locdate, String dateName, String isHoliday) {}
}
