package finalmission.payment.service.client.dto;

public record KakaoPaymentCancelRequest(
        String cid,
        String tid,
        Integer cancel_amount,
        Integer cancel_tax_free_amount
) {
}
