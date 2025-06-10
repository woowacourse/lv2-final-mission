package finalmission.user.service;

import finalmission.user.domain.Customer;
import finalmission.user.service.dto.CreateCustomerRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({CustomerService.class})
class CustomerServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomerService customerService;

    @DisplayName("중복되는 이메일의 계정은 생성할 수 없다.")
    @Test
    void cannotCreateDuplicatedEmailUser() {
        // given
        String name = "testUser";
        String email = "test@test.com";
        String password = "12341234";

        Customer customer = new Customer(name, email, password);
        entityManager.persist(customer);

        CreateCustomerRequest request = new CreateCustomerRequest(name, email, password);

        // when & then
        assertThatThrownBy(() -> {
            customerService.create(request);
        }).isInstanceOf(IllegalArgumentException.class);
     }
}
