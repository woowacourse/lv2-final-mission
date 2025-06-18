package finalmission.customer.service;

import finalmission.customer.domain.Customer;
import finalmission.customer.dto.request.CustomerCreateRequest;
import finalmission.customer.dto.response.CustomerResponse;
import finalmission.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse creatUser(CustomerCreateRequest request) {
        Customer withoutId = Customer.createWithoutId(request.email(), request.password());
        Customer savedCustomer = customerRepository.save(withoutId);
        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getEmail());
    }
}
