package finalmission.customer.controller;

import finalmission.customer.controller.dto.request.CustomerCreateRequest;
import finalmission.customer.controller.dto.response.CustomerCreateResponse;
import finalmission.customer.entity.Customer;
import finalmission.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerCreateResponse> create(
            @Valid @RequestBody CustomerCreateRequest customerCreateRequest
    ) {
        CustomerCreateResponse save = customerService.save(customerCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
}
