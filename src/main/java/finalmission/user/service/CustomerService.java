package finalmission.user.service;

import finalmission.user.domain.Customer;
import finalmission.user.repository.CustomerRepository;
import finalmission.user.service.dto.CreateCustomerRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void create(CreateCustomerRequest request) {
        Customer customer = request.toEntity();

        validateDuplicated(customer);
        customerRepository.save(customer);
    }

    private void validateDuplicated(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("이미 가입한 이메일 입니다.");
        }
    }
}
