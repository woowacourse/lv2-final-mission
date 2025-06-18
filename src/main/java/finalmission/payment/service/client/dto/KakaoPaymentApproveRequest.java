package finalmission.payment.service.client.dto;

public record KakaoPaymentApproveRequest(
        String cid,
        String tid,
        String partner_order_id,
        String partner_user_id,
        String pg_token
) {
}
