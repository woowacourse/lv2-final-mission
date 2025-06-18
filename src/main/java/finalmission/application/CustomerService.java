package finalmission.application;

import finalmission.domain.customer.Customer;
import finalmission.domain.customer.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(final String email,
                                   final String password,
                                   final String name,
                                   final String phoneNumber
    ) {
        Customer customer = Customer.register(email, password, name, phoneNumber);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void removeCustomer(final Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
