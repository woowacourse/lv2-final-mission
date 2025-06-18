package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateBlackListRequest;
import finalmission.dto.response.BlackListResponse;
import finalmission.dto.response.CreateBlackListResponse;
import finalmission.service.BlackListService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CheckRole({MemberRole.ADMIN})
@RestController
@RequestMapping("/blacklists")
public class BlackListController {

    private final BlackListService blackListService;

    public BlackListController(final BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    @GetMapping
    public List<BlackListResponse> getAllBlackList() {
        return blackListService.findAllBlackList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBlackListResponse addBlackList(@Valid @RequestBody CreateBlackListRequest request) {
        return blackListService.addBlackList(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlackList(@PathVariable("id") Long id) {
        blackListService.deleteBlackList(id);
    }
}
