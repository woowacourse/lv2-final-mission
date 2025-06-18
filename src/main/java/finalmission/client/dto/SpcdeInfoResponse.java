package finalmission.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;


public record SpcdeInfoResponse(
        Header header,
        Body body
) {
    public record Header(
            String resultCode,
            String resultMsg
    ) {
    }

    public record Body(
            Items items,
            Integer numOfRows,
            Integer pageNo,
            Integer totalCount
    ) {
    }

    public record Items(
            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            List<Item> item
    ) {
    }

    public record Item(
            @JsonFormat(pattern = "yyyy-MM-dd")
            Integer locdate,
            Integer seq,
            String dateKind,
            String isHoliday,
            String dateName
    ) {
    }
}
