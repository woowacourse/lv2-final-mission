package finalmission.customer.service;

import finalmission.customer.controller.dto.request.CustomerCreateRequest;
import finalmission.customer.controller.dto.response.CustomerCreateResponse;
import finalmission.customer.entity.Customer;
import finalmission.customer.repository.CustomerJpaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerJpaRepository customerJpaRepository;
    private final RandomNameRestClient randomNameRestClient;

    public CustomerService(CustomerJpaRepository customerJpaRepository,
                           RandomNameRestClient randomNameRestClient) {
        this.customerJpaRepository = customerJpaRepository;
        this.randomNameRestClient = randomNameRestClient;
    }

    public CustomerCreateResponse save(@Valid CustomerCreateRequest memberCreateResponse) {
        String name = memberCreateResponse.name();
        String email = memberCreateResponse.email();
        String password = memberCreateResponse.password();
        String nickName = randomNameRestClient.getRandomName();
        Customer customer = new Customer(name, nickName, email, password);
        Customer save = customerJpaRepository.save(customer);
        return CustomerCreateResponse.of(save);
    }
}
