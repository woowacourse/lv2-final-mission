package finalmission.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String nickName;

    @Column
    private String email;

    @Column
    private String password;

    protected Customer() {

    }

    public Customer(String name, String nickName, String email, String password) {
        this(null, name, nickName, email, password);
    }

    public Customer(Long id, String name, String nickName, String email, String password) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public boolean isSameId(Long customerId) {
        return Objects.equals(id, customerId);
    }
}
