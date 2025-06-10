package finalmission.client;

import io.jsonwebtoken.Header;

public record HolidayResponse(
        Response response
) {

    public record Response(
            Object header,
            Body body
    ) {
    }

    public record Body(
            HolidayItems items,
            int numOfRows,
            int pageNo,
            int totalCount
    ) {
    }

    public record HolidayItems(
            HolidayItem[] item
    ) {
    }
}
