package lavatoryreservation.toilet.service;

import lavatoryreservation.exception.ToiletException;
import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.lavatory.service.LavatoryService;
import lavatoryreservation.toilet.domain.Toilet;
import lavatoryreservation.toilet.dto.AddToiletDto;
import lavatoryreservation.toilet.repository.ToiletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToiletService {

    private final ToiletRepository toiletRepository;
    private final LavatoryService lavatoryService;

    public ToiletService(ToiletRepository toiletRepository, LavatoryService lavatoryService) {
        this.toiletRepository = toiletRepository;
        this.lavatoryService = lavatoryService;
    }

    public Long addToilet(AddToiletDto addToiletDto) {
        Lavatory lavatory = lavatoryService.getById(addToiletDto.lavatoryId());
        validateDuplicateDescriptionToilet(addToiletDto.description(), addToiletDto.lavatoryId());
        Toilet toilet = new Toilet(null, addToiletDto.description(), addToiletDto.isBidet(), lavatory);
        return toiletRepository.save(toilet).getId();
    }

    private void validateDuplicateDescriptionToilet(String description, Long lavatoryId) {
        if (toiletRepository.existsByDescriptionAndLavatory_Id(description, lavatoryId)) {
            throw new ToiletException("중복된 설명을 가진 화장실칸입니다");
        }
    }

    public Toilet getById(Long id) {
        return toiletRepository.findById(id)
                .orElseThrow(() -> new ToiletException("존재하지 않는 화장실칸입니다"));
    }

    public List<Toilet> getToilets() {
        return toiletRepository.findAll();
    }
}
