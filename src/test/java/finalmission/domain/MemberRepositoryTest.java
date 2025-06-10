package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자를 저장한다.")
    void save() {
        var member = new Member("포포");

        var savedMember = memberRepository.save(member);

        assertThat(memberRepository.findById(savedMember.getId())).hasValue(member);
    }
}
