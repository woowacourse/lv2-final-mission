package finalmission.client.dto;

public record SpcdeInfoConfirmRequest(
        String serviceKey,
//        Integer pageNo,
//        Integer numOfRows,
        Integer solYear,
        Integer solMonth,
        String _type
) {
}
