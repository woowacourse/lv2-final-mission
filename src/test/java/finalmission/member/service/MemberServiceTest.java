package finalmission.member.service;

import finalmission.auth.JwtTokenHandler;
import finalmission.member.domain.Member;
import finalmission.member.domain.vo.Role;
import finalmission.member.service.dto.request.CreateMemberRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({MemberService.class, JwtTokenHandler.class})
class MemberServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MemberService memberService;

    @DisplayName("중복되는 이메일의 계정은 생성할 수 없다.")
    @Test
    void cannotCreateDuplicatedEmailUser() {
        // given
        String name = "testUser";
        String email = "test@test.com";
        String password = "12341234";

        Member member = new Member(Role.CUSTOMER, name, email, password);
        entityManager.persist(member);

        CreateMemberRequest request = new CreateMemberRequest(name, email, password, Role.CUSTOMER.name());

        // when & then
        assertThatThrownBy(() -> {
            memberService.create(request);
        }).isInstanceOf(IllegalArgumentException.class);
     }
}
