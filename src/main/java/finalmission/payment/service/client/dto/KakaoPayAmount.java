package finalmission.payment.service.client.dto;

public record KakaoPayAmount(
        int total,
        int tax_free,
        int vat,
        int point,
        int discount,
        int green_deemed_amount
) {

}
