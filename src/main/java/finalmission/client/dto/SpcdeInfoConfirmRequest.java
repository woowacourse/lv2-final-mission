package finalmission.client.dto;

public record SpcdeInfoConfirmRequest(
        String serviceKey,
//        Integer pageNo,
//        Integer numOfRows,
        String solYear,
        String solMonth,
        String _type
) {
}
