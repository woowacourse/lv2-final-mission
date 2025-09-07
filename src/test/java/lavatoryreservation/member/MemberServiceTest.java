package lavatoryreservation.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lavatoryreservation.lavatory.domain.Sex;
import lavatoryreservation.member.dto.SignupDto;
import lavatoryreservation.member.repository.MemberRepository;
import lavatoryreservation.member.service.MemberService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;

    @Autowired
    public MemberServiceTest(MemberRepository memberRepository) {
        this.memberService = new MemberService(memberRepository);
        this.memberRepository = memberRepository;
    }

    @Test
    void 멤버를_생성할_수_있다() {
        SignupDto member = new SignupDto("투다", "praisebak@naver.com", Sex.MEN);
        memberService.addMember(member);
        assertThat(memberRepository.count()).isEqualTo(1L);
    }

    @Test
    void 중복된_이메일을_가진_멤버는_생길_수_없다() {
        SignupDto member = new SignupDto("투다", "praisebak@naver.com", Sex.MEN);
        memberService.addMember(member);
        SignupDto duplicatEmailMember = new SignupDto("투다", "praisebak@naver.com", Sex.MEN);
        assertThatThrownBy(() -> memberService.addMember(duplicatEmailMember)).isInstanceOf(
                IllegalArgumentException.class);
    }
}
