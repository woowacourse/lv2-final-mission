package woowaTable.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.common.exception.error.NotFoundException;
import woowaTable.user.application.dto.LoginCheckRequest;
import woowaTable.user.domain.Customer;
import woowaTable.user.domain.Email;
import woowaTable.user.domain.Owner;
import woowaTable.user.domain.Password;
import woowaTable.user.domain.Role;
import woowaTable.user.domain.User;
import woowaTable.user.domain.repository.CustomerRepository;
import woowaTable.user.domain.repository.OwnerRepository;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final CustomerRepository customerRepository;
    private final OwnerRepository ownerRepository;

    public Customer findCustomerByEmailAndPassword(final String email, final String password) {
        return customerRepository.findByEmailAndPassword(Email.from(email), Password.from(password))
                .orElseThrow(() -> new NotFoundException("해당하는 손님이 없습니다."));
    }

    public Owner findOwnerByEmailAndPassword(final String email, final String password) {
        return ownerRepository.findByEmailAndPassword(Email.from(email), Password.from(password))
                .orElseThrow(() -> new NotFoundException("해당하는 사장님이 없습니다."));
    }

    public User findByIdAndRole(final LoginCheckRequest request) {
        if (request.role() == Role.CUSTOMER) {
            return customerRepository.findById(request.id())
                    .orElseThrow(() -> new NotFoundException("해당하는 손님이 없습니다."));
        }
        return ownerRepository.findById(request.id())
                .orElseThrow(() -> new NotFoundException("해당하는 사장님이 없습니다."));
    }

    public boolean existsCustomerByEmail(final Email email) {
        return customerRepository.existsByEmail(email);
    }

    public boolean existsOwnerByEmail(final Email email) {
        return ownerRepository.existsByEmail(email);
    }
}
