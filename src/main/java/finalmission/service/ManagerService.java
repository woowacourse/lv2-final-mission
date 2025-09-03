package finalmission.service;

import finalmission.domain.entity.Manager;
import finalmission.dto.ManagerCreateRequest;
import finalmission.dto.ManagerResponse;
import finalmission.repository.ManagerRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Transactional(readOnly = true)
    public List<ManagerResponse> getAllManagers() {
        List<Manager> managers = managerRepository.findAll();
        List<ManagerResponse> responses = new ArrayList<>();
        for (Manager manager : managers) {
            responses.add(ManagerResponse.from(manager));
        }
        return responses;
    }

    public ManagerResponse createManager(ManagerCreateRequest request) {
        Manager saved = managerRepository.save(new Manager(
                request.name(),
                request.phoneNumber()
        ));
        return ManagerResponse.from(saved);
    }

    public void deleteManager(Long managerId) {
        managerRepository.deleteById(managerId);
    }
}
