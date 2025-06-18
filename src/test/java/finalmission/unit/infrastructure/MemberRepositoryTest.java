package finalmission.unit.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.Member;
import finalmission.infrastructure.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(value = "/sql/member.sql")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 이메일로_찾기() {
        //when & then
        Optional<Member> member = memberRepository.findByEmail("kim@email.com");

        assertThat(member).isPresent();
    }
}
