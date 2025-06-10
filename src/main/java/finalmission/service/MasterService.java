package finalmission.service;

import finalmission.domain.Master;
import finalmission.dto.request.MasterCreateRequest;
import finalmission.dto.response.MasterCreateResponse;
import finalmission.repository.MasterRepository;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

    private final MasterRepository masterRepository;

    public MasterService(final MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public MasterCreateResponse save(MasterCreateRequest masterCreateRequest) {
        Master master = new Master(masterCreateRequest.email(), masterCreateRequest.name(),
                masterCreateRequest.password());
        return MasterCreateResponse.from(masterRepository.save(master));
    }
}
