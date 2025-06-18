package finalmission.payment.service;

import finalmission.concert.domain.Concert;
import finalmission.concert.service.detail.ConcertQueryService;
import finalmission.payment.controller.dto.PaymentReadyRequest;
import finalmission.payment.controller.dto.PaymentReadyResponse;
import finalmission.payment.domain.Payment;
import finalmission.payment.service.client.KakaoPaymentClient;
import finalmission.payment.service.client.dto.KakaoPaymentApproveRequest;
import finalmission.payment.service.client.dto.KakaoPaymentApproveResponse;
import finalmission.payment.service.client.dto.KakaoPaymentCancelRequest;
import finalmission.payment.service.client.dto.KakaoPaymentReadyRequest;
import finalmission.payment.service.client.dto.KakaoPaymentReadyResponse;
import finalmission.payment.service.detail.PaymentCommandService;
import finalmission.payment.service.detail.PaymentQueryService;
import finalmission.payment.service.dto.PaymentApproveRequest;
import finalmission.payment.service.dto.PaymentApproveResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.service.detail.ReservationQueryService;
import finalmission.seat.domain.Seat;
import finalmission.seat.service.detail.SeatQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentFrontService {

    @Value("${payment.cid}")
    private String CID;

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;
    private final KakaoPaymentClient kakaoPaymentClient;
    private final ReservationQueryService reservationQueryService;
    private final SeatQueryService seatQueryService;
    private final ConcertQueryService concertQueryService;

    public PaymentReadyResponse ready(final PaymentReadyRequest request) {
        Concert concert = concertQueryService.get(request.concertId());
        Seat seat = seatQueryService.get(request.seatId());

        final KakaoPaymentReadyRequest readyRequest = new KakaoPaymentReadyRequest(
                CID,
                "temp_partner_order_id",
                "temp_partner_user_id",
                concert.getTitle() + " - " + seat.getSeatNumber(),
                1,
                concert.getPrice().intValue(),
                0,
                "http://localhost:8080/payments/confirm",
                "http://localhost:8080/payments",
                "http://localhost:8080/payments"
        );

        final KakaoPaymentReadyResponse readyResponse = kakaoPaymentClient.ready(readyRequest);

        return new PaymentReadyResponse(readyResponse.tid(), readyResponse.next_redirect_pc_url());
    }

    public PaymentApproveResponse approve(final PaymentApproveRequest request) {
        final Reservation reservation = reservationQueryService.get(request.reservationId());

        final KakaoPaymentApproveRequest approveRequest = new KakaoPaymentApproveRequest(
                CID,
                request.tid(),
                "temp_partner_order_id",
                "temp_partner_user_id",
                request.pgToken()
        );

        final KakaoPaymentApproveResponse approveResponse = kakaoPaymentClient.approve(approveRequest);

        final Payment payment = new Payment(
                approveResponse.tid(),
                (long) approveResponse.getTotalAmount(),
                reservation,
                approveResponse.requested_at(),
                approveResponse.approved_at()
        );

        final Payment savedPayment = paymentCommandService.create(payment);

        return new PaymentApproveResponse(
                savedPayment.getId(),
                savedPayment.getReservation().getId(),
                savedPayment.getTid(),
                savedPayment.getAmount()
        );
    }

    public void cancel(final Long reservationId) {
        final Payment payment = paymentQueryService.getByReservationId(reservationId);

        final KakaoPaymentCancelRequest cancelRequest = new KakaoPaymentCancelRequest(
                CID,
                payment.getTid(),
                payment.getAmount().intValue(),
                0
        );

        kakaoPaymentClient.cancel(cancelRequest);

        paymentCommandService.delete(payment);
    }

    public PaymentApproveResponse get(Long id) {
        final Payment payment = paymentQueryService.get(id);

        return new PaymentApproveResponse(
                payment.getId(),
                payment.getReservation().getId(),
                payment.getTid(),
                payment.getAmount()
        );
    }
}
