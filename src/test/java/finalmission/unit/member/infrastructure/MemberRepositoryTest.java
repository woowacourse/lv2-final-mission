package finalmission.unit.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.member.domain.Member;
import finalmission.member.infrastructure.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원을_저장한다() {
        // given
        Member member = new Member("email1@domain.com", "nickname", "password");
        // when
        Member savedMember = memberRepository.save(member);
        // then
        Member foundMember = entityManager.find(Member.class, savedMember.getId());
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo("email1@domain.com");
        assertThat(foundMember.getNickname()).isEqualTo("nickname");
        assertThat(foundMember.getPassword()).isEqualTo("password");
    }

    @Test
    void 이메일이_존재하면_true를_반환한다() {
        // given
        Member member = new Member("email@domain.com", "nickname", "password");
        entityManager.persist(member);
        // when
        boolean exists = memberRepository.existsByEmail("email@domain.com");
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 이메일이_존재하지_않으면_false를_반환한다() {
        // when
        boolean exists = memberRepository.existsByEmail("nonexistent@example.com");
        // then
        assertThat(exists).isFalse();
    }

    @Test
    void 이메일로_회원을_찾는다() {
        // given
        Member member = new Member("email@domain.com", "nickname", "password");
        entityManager.persist(member);
        // when
        Optional<Member> foundMember = memberRepository.findByEmail("email@domain.com");
        // then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("email@domain.com");
        assertThat(foundMember.get().getNickname()).isEqualTo("nickname");
        assertThat(foundMember.get().getPassword()).isEqualTo("password");
    }

    @Test
    void 이메일이_존재하지_않으면_빈_Optional을_반환한다() {
        // when
        Optional<Member> foundMember = memberRepository.findByEmail("nonexistent@example.com");
        // then
        assertThat(foundMember).isEmpty();
    }

    @Test
    void ID로_회원을_찾는다() {
        // given
        Member member = new Member("email@domain.com", "nickname", "password");
        entityManager.persist(member);
        // when
        Optional<Member> foundMember = memberRepository.findById(member.getId());
        // then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("email@domain.com");
        assertThat(foundMember.get().getNickname()).isEqualTo("nickname");
        assertThat(foundMember.get().getPassword()).isEqualTo("password");
    }

    @Test
    void ID가_존재하지_않으면_빈_Optional을_반환한다() {
        // when
        Optional<Member> foundMember = memberRepository.findById(999L);
        // then
        assertThat(foundMember).isEmpty();
    }
}