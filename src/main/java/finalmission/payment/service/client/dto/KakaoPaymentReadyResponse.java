package finalmission.payment.service.client.dto;

public record KakaoPaymentReadyResponse(
        String tid,
        String next_redirect_pc_url,
        String next_redirect_mobile_url,
        String android_app_scheme,
        String ios_app_scheme,
        String created_at
) {
}
