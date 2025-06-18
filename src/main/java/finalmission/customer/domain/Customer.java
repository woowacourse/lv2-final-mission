package finalmission.customer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private Customer(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Customer createWithoutId(String email, String password) {
        return new Customer(null, email, password);
    }
}
