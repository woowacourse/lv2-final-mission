package finalmission.payment.service.client.dto;

public record KakaoPaymentApproveResponse(
        String aid,
        String tid,
        String cid,
        String status,
        String partner_order_id,
        String partner_user_id,
        String payment_method_type,
        KakaoPayAmount amount,
        KakaoPayAmount cancelled_amount,
        KakaoPayAmount refundable_amount,
        String item_name,
        String item_code,
        String requested_at,
        String approved_at,
        String created_at,
        String payload
) {

    public int getTotalAmount() {
        return amount.total();
    }
}
