package lavatoryreservation.toilet.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.lavatory.domain.Sex;
import lavatoryreservation.lavatory.repository.LavatoryRepository;
import lavatoryreservation.lavatory.service.LavatoryService;
import lavatoryreservation.toilet.dto.AddToiletDto;
import lavatoryreservation.toilet.repository.ToiletRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ToiletServiceTest {

    private final ToiletRepository toiletRepository;
    private final LavatoryService lavatoryService;
    private final ToiletService toiletService;

    @Autowired
    public ToiletServiceTest(ToiletRepository toiletRepository, LavatoryRepository lavatoryRepository) {
        this.toiletRepository = toiletRepository;
        this.lavatoryService = new LavatoryService(lavatoryRepository);
        this.toiletService = new ToiletService(toiletRepository, lavatoryService);
    }

    @Test
    void 화장실칸을_추가할_수_있다() {
        Lavatory lavatory = new Lavatory(null, Sex.MEN, "description");
        lavatoryService.addLavatory(lavatory);
        toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        assertThat(toiletRepository.count()).isEqualTo(1L);
    }

    @Test
    void 동일한_화장실의_화장실칸_설명이_동일할_수_없다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "description"));
        Long toiletId = toiletService.addToilet(new AddToiletDto("1번칸", false, lavatory.getId()));

        AddToiletDto duplicateDescriptionToilet = new AddToiletDto("1번칸", false, lavatory.getId());
        assertThatThrownBy(() -> toiletService.addToilet(duplicateDescriptionToilet)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 다른_화장실의_화장실칸_설명은_동일할_수_있다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "description"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Lavatory secondLavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "description2"));

        AddToiletDto duplicateDescriptionToilet = new AddToiletDto("1번칸", false, secondLavatory.getId());
        toiletService.addToilet(duplicateDescriptionToilet);
        assertThat(toiletRepository.count()).isEqualTo(2L);
    }
}
