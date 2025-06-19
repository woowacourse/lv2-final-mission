package finalmission.domain.service;

import finalmission.domain.entity.Gender;
import finalmission.domain.entity.Member;
import finalmission.domain.entity.Trainer;
import finalmission.domain.repository.MemberRepository;
import finalmission.domain.repository.TrainerRepository;
import finalmission.util.AbstractServiceTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class MemberServiceTest extends AbstractServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TrainerRepository trainerRepository;

    @DisplayName("담당 선생님이 없다면 담당 선생님을 등록한다")
    @Test
    @Transactional
    void selectTrainer() {
        // given
        Member member = Member.createWithoutId("user", "user@email.com", "1234", 2);
        Trainer trainer = Trainer.createWithoutId("trainer", Gender.WOMAN);
        memberRepository.save(member);
        trainerRepository.save(trainer);

        // when
        // then
        Assertions.assertThatCode(() -> memberService.selectTrainer(member, trainer.getId()))
                .doesNotThrowAnyException();
    }
}