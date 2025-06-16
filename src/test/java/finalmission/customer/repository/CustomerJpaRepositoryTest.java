package finalmission.customer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.customer.entity.Customer;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerJpaRepositoryTest {

    @Autowired
    CustomerJpaRepository customerJpaRepository;

    private String name;
    private String nickName;
    private String email;
    private String password;
    private Customer customer;

    @BeforeEach
    void setUp() {
        name = "neo";
        nickName = "woowaNeo";
        email = "neo@com";
        password = "1234";
        customer = new Customer(name, nickName, email, password);
    }

    @Test
    @DisplayName("이름으로 회원을 찾는다.")
    void findByNameTest() {
        //given
        customerJpaRepository.save(customer);

        //when
        Optional<Customer> response = customerJpaRepository.findByName("neo");
        Customer neo = response.get();

        //then
        assertThat(neo.getName()).isEqualTo(name);
        assertThat(neo.getEmail()).isEqualTo(email);
        assertThat(neo.getNickName()).isEqualTo(nickName);
        assertThat(neo.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("이메일과 비밀번호로 회원을 존재 여부를 확인한다.")
    void existsByEmailAndPasswordTest() {
        //given
        customerJpaRepository.save(customer);

        //when
        boolean response = customerJpaRepository.existsByEmailAndPassword(email, password);

        //then
        assertThat(response).isTrue();
    }

    @Test
    @DisplayName("이메일로 회원을 조회한다.")
    void findByEmailTest() {
        //given
        customerJpaRepository.save(customer);

        //when
        Optional<Customer> response = customerJpaRepository.findByEmail(email);
        Customer neo = response.get();

        //then
        assertThat(neo.getName()).isEqualTo(name);
        assertThat(neo.getEmail()).isEqualTo(email);
        assertThat(neo.getNickName()).isEqualTo(nickName);
        assertThat(neo.getPassword()).isEqualTo(password);
    }
}
