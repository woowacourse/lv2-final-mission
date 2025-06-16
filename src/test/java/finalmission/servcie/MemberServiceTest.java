package finalmission.servcie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.domain.Member;
import finalmission.domain.Role;
import finalmission.dto.layer.MemberCreationContent;
import finalmission.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(value = {MemberService.class})
class MemberServiceTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MemberService memberService;

    @Nested
    @DisplayName("회원을 추가할 수 있다.")
    public class AddMember {

        @DisplayName("정상적으로 회원을 추가할 수 있다.")
        @Test
        void canAddMember() {
            // given
            MemberCreationContent creationContent =
                    new MemberCreationContent("test@test.ocm", "qwer1234!", "Kim");

            // when
            Member member = memberService.addMember(creationContent);

            // then
            assertThat(entityManager.find(Member.class, member.getId())).isNotNull();
        }

        @DisplayName("이메일이 중복된 경우 회원추가가 불가능하다.")
        @Test
        void cannotAddMemberByDuplicatedEmail() {
            // given
            Member member = entityManager.persist(new Member("test@test.ocm", "qwer1234!", "Kim", Role.GENERAL));
            MemberCreationContent creationContent =
                    new MemberCreationContent(member.getEmail(), "qwer1234!", "Kim");

            entityManager.flush();
            entityManager.clear();

            // when & then
            assertThatThrownBy(() -> memberService.addMember(creationContent))
                    .isInstanceOf(BadRequestException.class);
        }
    }
}
