package finalmission.umbrella.service;

import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.domain.UmbrellaType;
import finalmission.umbrella.dto.UmbrellaResponse;
import finalmission.umbrella.infrastructure.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UmbrellaService {

    private final UmbrellaRepository jpaUmbrellaRepository;

    public List<UmbrellaResponse> findAllUmbrella(){
        List<Umbrella> allUmbrellas = jpaUmbrellaRepository.findAll();
        return allUmbrellas.stream().map(UmbrellaResponse::from).toList();
    }

    public long countAllUmbrella(){
        return jpaUmbrellaRepository.count();
    }

    public long countUmbrellaBySize(UmbrellaType umbrellaType){
        return jpaUmbrellaRepository.findByUmbrellaType(umbrellaType).size();
    }
}
