package woowaTable.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.user.domain.Customer;
import woowaTable.user.domain.Owner;
import woowaTable.user.domain.repository.CustomerRepository;
import woowaTable.user.domain.repository.OwnerRepository;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final CustomerRepository customerRepository;
    private final OwnerRepository ownerRepository;

    public Customer saveCustomer(final Customer customer) {
        return customerRepository.save(customer);
    }

    public Owner saveOwner(final Owner owner) {
        return ownerRepository.save(owner);
    }
}
