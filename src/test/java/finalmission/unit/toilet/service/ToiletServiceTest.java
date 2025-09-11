package finalmission.unit.toilet.service;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.toilet.domain.Toilet;
import finalmission.toilet.dto.response.ToiletResponse;
import finalmission.toilet.infrastructure.ToiletRepository;
import finalmission.toilet.service.ToiletService;
import finalmission.unit.fake.FakeToiletRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ToiletServiceTest {

    private final ToiletService toiletService;
    private final ToiletRepository toiletRepository = new FakeToiletRepository();

    public ToiletServiceTest() {
        this.toiletService = new ToiletService(toiletRepository);
    }

    @Test
    void 화장실_목록을_조회한다() {
        // given
        toiletRepository.save(new Toilet("position1"));
        toiletRepository.save(new Toilet("position2"));
        toiletRepository.save(new Toilet("position3"));
        // when
        List<ToiletResponse> toilets = toiletService.findToilets();
        // then
        assertThat(toilets).hasSize(3);
    }
}