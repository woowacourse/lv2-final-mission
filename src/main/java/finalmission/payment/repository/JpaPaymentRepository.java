package finalmission.payment.repository;

import finalmission.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long>, PaymentRepository {

}
