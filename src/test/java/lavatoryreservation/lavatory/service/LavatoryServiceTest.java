package lavatoryreservation.lavatory.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.lavatory.domain.Sex;
import lavatoryreservation.lavatory.repository.LavatoryRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LavatoryServiceTest {

    private LavatoryService lavatoryService;
    private LavatoryRepository lavatoryRepository;

    @Autowired
    public LavatoryServiceTest(LavatoryRepository lavatoryRepository) {
        this.lavatoryRepository = lavatoryRepository;
        this.lavatoryService = new LavatoryService(lavatoryRepository);
    }

    @Test
    void 화장실을_추가할_수_있다() {
        lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        assertThat(lavatoryRepository.count()).isEqualTo(1L);
    }

    @Test
    void 중복된_설명의_화장실을_추가할_수_없다() {
        lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Lavatory duplicateDescriptionLavatory = new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실");
        assertThatThrownBy(() -> lavatoryService.addLavatory(duplicateDescriptionLavatory)).isInstanceOf(
                IllegalArgumentException.class);
    }
}


