package finalmission.unit.infrastructure;

import finalmission.domain.Member;
import finalmission.infrastructure.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void 회원을_저장한다() {
        // given
        Member member = new Member("email1@domain.com", "이름1", "1234");
        // when
        Member savedMember = memberRepository.save(member);
        // then
        Member findMember = entityManager.find(Member.class, savedMember.getId());
        Assertions.assertThat(findMember).isNotNull();
    }
}
