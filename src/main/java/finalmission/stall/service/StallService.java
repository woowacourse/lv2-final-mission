package finalmission.stall.service;

import finalmission.exception.ConflictException;
import finalmission.exception.NotFoundException;
import finalmission.stall.controller.dto.request.StallCreateRequest;
import finalmission.stall.controller.dto.response.StallCreateResponse;
import finalmission.stall.controller.dto.response.StallInfoResponse;
import finalmission.stall.controller.dto.response.StallInfosResponse;
import finalmission.stall.entity.Stall;
import finalmission.stall.repository.StallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StallService {

    private final StallRepository stallRepository;

    public StallService(StallRepository stallRepository) {
        this.stallRepository = stallRepository;
    }

    public StallCreateResponse create(StallCreateRequest request) {
        validateConflict(request);
        Stall stall = request.toStall();
        Stall savedStall = stallRepository.save(stall);
        return StallCreateResponse.from(savedStall);
    }

    private void validateConflict(StallCreateRequest request) {
        String stallName = request.name();
        boolean isExists = stallRepository.existsByName(stallName);
        if (isExists) {
            throw new ConflictException("같은 이름의 사로가 존재합니다.");
        }
    }

    public StallInfosResponse findStalls() {
        List<Stall> stalls = stallRepository.findAll();
        List<StallInfoResponse> mapToResponse = stalls.stream().map(StallInfoResponse::from).toList();
        return new StallInfosResponse(mapToResponse);
    }

    public void deleteById(Long id) {
        validateDeletableStall(id);
        stallRepository.deleteById(id);
    }

    private void validateDeletableStall(Long id) {
        stallRepository.findById(id).orElseThrow(() -> new NotFoundException("삭제 가능한 사로가 존재하지 않습니다"));
        // todo : 사로가 이용중인 경우(사용중 or 대기) 삭제 불가능 한 로직 추가
    }
}
