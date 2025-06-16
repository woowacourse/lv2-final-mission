package finalmission.omakase.service;

import finalmission.omakase.controller.dto.OmakaseCreateRequest;
import finalmission.omakase.entity.Omakase;
import finalmission.omakase.entity.Rating;
import finalmission.omakase.repository.OmakaseJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OmakaseService {

    private final OmakaseJpaRepository omakaseJpaRepository;

    public OmakaseService(OmakaseJpaRepository omakaseJpaRepository) {
        this.omakaseJpaRepository = omakaseJpaRepository;
    }

    public Omakase save(OmakaseCreateRequest omakaseCreateRequest) {
        String name = omakaseCreateRequest.name();
        Rating rating = omakaseCreateRequest.rating();
        Omakase omakase = new Omakase(name, rating);
        return omakaseJpaRepository.save(omakase);
    }
}
