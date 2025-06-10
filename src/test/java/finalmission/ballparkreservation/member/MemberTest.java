package finalmission.ballparkreservation.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("할인이 적용되는 연령대인지 확인할 수 있다.")
    @Test
    void checkAgeForDiscount() {
        // given
        Member member = new Member("may@gamil.com", "1234", "메이", 13);
        
        // when & then
        Assertions.assertThat(member.isDiscountApply()).isTrue();
    }
}
