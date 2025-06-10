package finalmission.payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String orderId;

    @Getter
    private LocalDateTime paymentDateTime;

    private Long amount;

    private String paymentKey;

    private Payment(Long id, String orderId, LocalDateTime paymentDateTime, Long amount, String paymentKey) {
        this.id = id;
        this.orderId = orderId;
        this.paymentDateTime = paymentDateTime;
        this.amount = amount;
        this.paymentKey = paymentKey;
    }

    public static Payment createWithoutId(final String orderId, final LocalDateTime paymentDateTime, final Long amount, final String paymentKey){
        return new Payment(null, orderId, paymentDateTime, amount, paymentKey);
    }
}
