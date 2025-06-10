package finalmission.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.Toilet;
import finalmission.dto.ToiletResponse;
import finalmission.infrastructure.ToiletRepository;
import finalmission.service.ToiletService;
import finalmission.unit.fake.FakeToiletRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ToiletServiceTest {

    private ToiletService toiletService;

    private ToiletRepository toiletRepository;

    public ToiletServiceTest() {
        toiletRepository = new FakeToiletRepository();
        this.toiletService = new ToiletService(toiletRepository);
    }

    @Test
    void 전체_조회한다() {
        // given
        toiletRepository.save(new Toilet("루터회관 14층 1번칸"));
        // when
        List<ToiletResponse> allToilets = toiletService.findAllToilets();
        // then
        assertThat(allToilets).hasSize(1);
        assertThat(allToilets.get(0).position()).isEqualTo("루터회관 14층 1번칸");
    }
}
