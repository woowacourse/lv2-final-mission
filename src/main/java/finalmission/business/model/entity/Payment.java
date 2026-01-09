package finalmission.business.model.entity;

import finalmission.presentation.dto.PaymentApproveResponseDto;
import finalmission.presentation.dto.PaymentInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToOne
    private Reservation reservation;
    private String paymentKey;
    private String orderId;
    private Long amount;

    protected Payment() {
    }

    public static Payment create(final Reservation reservation, final PaymentInfo paymentInfo) {
        return new Payment(null, reservation, paymentInfo.paymentKey(), paymentInfo.orderId(), paymentInfo.amount());
    }

    public static Payment create(final Reservation reservation,
                                 final PaymentApproveResponseDto paymentApproveResponseDto) {
        return new Payment(null, reservation, paymentApproveResponseDto.paymentKey(),
                paymentApproveResponseDto.orderId(), paymentApproveResponseDto.totalAmount());
    }

}
